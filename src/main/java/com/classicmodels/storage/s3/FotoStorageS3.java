package com.classicmodels.storage.s3;

import com.classicmodels.storage.FotoStorage;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Component
public class FotoStorageS3 implements FotoStorage {

    private AwsCredentials credentials;
    private S3Client s3Client;

    @Value("${BUCKET}")
    private String bucket;

    @Value("${ACCESS_KEY}")
    private String accessKey;

    @Value("${SECRET_KEY}")
    private String secretKey;

    @Value("${REGION_NAME}")
    private String regionName;

    private String extension;
    private String folderPrefix;

    public FotoStorageS3() {

    }

    @PostConstruct
    public void init() {

        folderPrefix = "productLinesImages/";

        this.credentials = AwsBasicCredentials.create(accessKey, secretKey);
        this.s3Client = S3Client.builder()
                .region(Region.of(regionName))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Override
    public String salvar(MultipartFile file, String nome){

       if( file != null && file.getSize() > 0) {
           try {
                enviarFoto(nome, file);
            } catch (IOException e) {
                throw new RuntimeException("Error saving file on S3", e);
            }
       }
       return nome;
    }

    private Map<String, String> enviarFoto(String nome, MultipartFile arquivo) throws IOException{

        String aux = arquivo.getOriginalFilename();
        extension = "";

        if (aux != null && aux.contains(".")) {
            extension = aux.substring(aux.lastIndexOf(".") + 1);
        }

        Map<String, String> metadata = new HashMap<>();
//        assert arquivo != null;
        metadata.put("Content Type", arquivo.getContentType());
        metadata.put("Size", String.valueOf(arquivo.getSize()));
        metadata.put("Environment", "Dev");
        metadata.put("Original Name", arquivo.getOriginalFilename());
        metadata.put("Extension", extension);

        PutObjectRequest por = PutObjectRequest.builder()
                        .bucket(bucket)
                                .key("%s%s.%s".formatted(folderPrefix, nome, extension))
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
