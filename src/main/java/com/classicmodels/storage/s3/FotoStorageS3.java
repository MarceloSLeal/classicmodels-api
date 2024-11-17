package com.classicmodels.storage.s3;

import com.classicmodels.storage.FotoStorage;
import io.github.cdimascio.dotenv.Dotenv;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

//@Profile("prod") inserir perfil?
@AllArgsConstructor
@Component
public class FotoStorageS3 implements FotoStorage {


    private Dotenv dotenv = Dotenv.load();
    private AwsCredentials credentials;
    private S3Client s3Client;

    public FotoStorageS3(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    @PostConstruct
    public void init() {
        String accessKey = dotenv.get("ACCESS_KEY");
        String secretKey = dotenv.get("SECRET_KEY");
        String regionName = dotenv.get("REGION_NAME");

        this.credentials = AwsBasicCredentials.create(accessKey, secretKey);
        this.s3Client = S3Client.builder()
                .region(Region.of(regionName))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Value("${BUCKET}")
    private String BUCKET = dotenv.get("BUCKET");

    @Override
    public String salvar(MultipartFile file, String nome){

       if( file != null && file.getSize() > 0) {

           try {
                enviarFoto(nome, file);
            } catch (IOException e) {
                throw new RuntimeException("Erro salvando arquivo no S3", e);
            }

       }
       return nome;
    }

    private Map<String, String> enviarFoto(String nome, MultipartFile arquivo) throws IOException{
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content Type", arquivo.getContentType());
        metadata.put("Size", String.valueOf(arquivo.getSize()));
        metadata.put("Environment", "Dev");

        PutObjectRequest por = PutObjectRequest.builder()
                        .bucket(BUCKET)
                                .key(nome)
                                        .metadata(metadata)
                                                .build();

        InputStream inputStream = arquivo.getInputStream();
        s3Client.putObject(por, RequestBody.fromInputStream(inputStream, arquivo.getSize()));

        return metadata;
    }

   // private AmazonS3 amazonS3;

//    @Override
//    public String salvar(MultipartFile[] files) {
//        String novoNome = null;
//        if (files != null && files.length > 0) {
//            MultipartFile arquivo = files[0];
//            novoNome = renomearArquivo(arquivo.getOriginalFilename());
//
//            try {
//
//                AccessControlList acl = new AccessControlList();
//                acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
//
//                enviarFoto(novoNome, arquivo, acl);
//                enviarThumbnail(novoNome, arquivo, acl);
//            } catch (IOException e) {
//                throw new RuntimeException("Erro salvando arquivo no S3", e);
//            }
//        }
//
//        return novoNome;
//    }
//
//    @Override
//    public byte[] recuperar(String foto) {
//        InputStream is = amazonS3.getObject(BUCKET, foto).getObjectContent();
//        try {
//            return IOUtils.toByteArray(is);
//        } catch (IOException e) {
//            //logger.error("Não conseguiu recuerar foto do S3", e);
//        }
//        return null;
//    }
//
//    @Override
//    public byte[] recuperarThumbnail(String foto) {
//        return recuperar(FotoStorage.THUMBNAIL_PREFIX + foto);
//    }
//
//    @Override
//    public void excluir(String foto) {
//        amazonS3.deleteObjects(new DeleteObjectsRequest(BUCKET).withKeys(foto, THUMBNAIL_PREFIX + foto));
//    }
//
//    @Override
//    public String getUrl(String foto) {
//        if (!StringUtils.isEmpty(foto)) {
//            return "https://s3-sa-east-1.amazonaws.com/awbrewer/" + foto;
//        }
//
//        return null;
//    }
//
//    private ObjectMetadata enviarFoto(String novoNome, MultipartFile arquivo, AccessControlList acl)
//            throws IOException {
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentType(arquivo.getContentType());
//        metadata.setContentLength(arquivo.getSize());
//        amazonS3.putObject(new PutObjectRequest(BUCKET, novoNome, arquivo.getInputStream(), metadata)
//                .withAccessControlList(acl));
//        return metadata;
//    }
//
//    private void enviarThumbnail(String novoNome, MultipartFile arquivo, AccessControlList acl)	throws IOException {
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        Thumbnails.of(arquivo.getInputStream()).size(40, 68).toOutputStream(os);
//        byte[] array = os.toByteArray();
//        InputStream is = new ByteArrayInputStream(array);
//        ObjectMetadata thumbMetadata = new ObjectMetadata();
//        thumbMetadata.setContentType(arquivo.getContentType());
//        thumbMetadata.setContentLength(array.length);
//        amazonS3.putObject(new PutObjectRequest(BUCKET, THUMBNAIL_PREFIX + novoNome, is, thumbMetadata)
//                .withAccessControlList(acl));
//    }

}
