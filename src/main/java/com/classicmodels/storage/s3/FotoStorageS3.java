package com.classicmodels.storage.s3;

import com.classicmodels.storage.FotoStorage;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;

import java.io.IOException;
import java.io.InputStream;

//@Profile("prod") inserir perfil?
@AllArgsConstructor
@Component
public class FotoStorageS3 implements FotoStorage {

    //TODO -- inserir logger
    //private static final Logger logger = LoggerFactory.getLogger(FotoStorageS3.class);

    //TODO -- criar variáveis de ambiente para essas chaves
    String accessKey = "<AWS Access Key>";
    String secretKey = "<AWS Secret Key>";
    AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

    private static final String BUCKET = "awbrewer";

   // private AmazonS3 amazonS3;

    @Override
    public String salvar(MultipartFile[] files) {
        String novoNome = null;
        if (files != null && files.length > 0) {
            MultipartFile arquivo = files[0];
            novoNome = renomearArquivo(arquivo.getOriginalFilename());

            try {
                AccessControlList acl = new AccessControlList();
                acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);

                enviarFoto(novoNome, arquivo, acl);
                enviarThumbnail(novoNome, arquivo, acl);
            } catch (IOException e) {
                throw new RuntimeException("Erro salvando arquivo no S3", e);
            }
        }

        return novoNome;
    }

    @Override
    public byte[] recuperar(String foto) {
        InputStream is = amazonS3.getObject(BUCKET, foto).getObjectContent();
        try {
            return IOUtils.toByteArray(is);
        } catch (IOException e) {
            //logger.error("Não conseguiu recuerar foto do S3", e);
        }
        return null;
    }

    @Override
    public byte[] recuperarThumbnail(String foto) {
        return recuperar(FotoStorage.THUMBNAIL_PREFIX + foto);
    }

    @Override
    public void excluir(String foto) {
        amazonS3.deleteObjects(new DeleteObjectsRequest(BUCKET).withKeys(foto, THUMBNAIL_PREFIX + foto));
    }

    @Override
    public String getUrl(String foto) {
        if (!StringUtils.isEmpty(foto)) {
            return "https://s3-sa-east-1.amazonaws.com/awbrewer/" + foto;
        }

        return null;
    }

    private ObjectMetadata enviarFoto(String novoNome, MultipartFile arquivo, AccessControlList acl)
            throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(arquivo.getContentType());
        metadata.setContentLength(arquivo.getSize());
        amazonS3.putObject(new PutObjectRequest(BUCKET, novoNome, arquivo.getInputStream(), metadata)
                .withAccessControlList(acl));
        return metadata;
    }

    private void enviarThumbnail(String novoNome, MultipartFile arquivo, AccessControlList acl)	throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Thumbnails.of(arquivo.getInputStream()).size(40, 68).toOutputStream(os);
        byte[] array = os.toByteArray();
        InputStream is = new ByteArrayInputStream(array);
        ObjectMetadata thumbMetadata = new ObjectMetadata();
        thumbMetadata.setContentType(arquivo.getContentType());
        thumbMetadata.setContentLength(array.length);
        amazonS3.putObject(new PutObjectRequest(BUCKET, THUMBNAIL_PREFIX + novoNome, is, thumbMetadata)
                .withAccessControlList(acl));
    }

}
