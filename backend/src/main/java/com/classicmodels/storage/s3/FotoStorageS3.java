package com.classicmodels.storage.s3;

import com.classicmodels.common.AwsS3Config;
import com.classicmodels.storage.FotoStorage;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.utils.IoUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@AllArgsConstructor
@NoArgsConstructor
@Component
public class FotoStorageS3 implements FotoStorage {

    @Autowired
    AwsS3Config s3Config;

    private String extension;
    private String folderPrefix;

    @PostConstruct
    public void init() {
        folderPrefix = "productLinesImages/";
    }

    @Override
    public String salvar(byte[] file, String contentType, String nome) {

        if (file != null && file.length > 0) {
            try {
                enviarFoto(nome, file, contentType);
            } catch (IOException e) {
                throw new RuntimeException("Error saving file on S3", e);
            }
        }
        return nome;
    }

    private void enviarFoto(String nome, byte[] fileContent, String contentType) throws IOException {

        String extension = switch (contentType.trim()) {
            case "image/png" -> "png";
            case "image/jpeg" -> "jpeg";
            default -> "jpeg";
        };

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content Type", contentType);
        metadata.put("Size", String.valueOf(fileContent.length));
        metadata.put("Environment", "Dev");
        metadata.put("Extension", extension);

        PutObjectRequest por = PutObjectRequest.builder()
                .bucket(s3Config.getBUCKET())
                .key("%s%s.%s".formatted(folderPrefix, nome, extension))
                .metadata(metadata)
                .cacheControl("public, max-age=" + s3Config.getCACHECONTROLMAXAGE())
                .build();

        InputStream inputStream = new ByteArrayInputStream(fileContent);
        s3Config.getS3().putObject(por, RequestBody.fromInputStream(inputStream, fileContent.length));
    }

    @Override
    public byte[] recuperar(String foto) {
        try {
            InputStream is = s3Config.getS3().getObject(request ->
                    request.bucket(s3Config.getBUCKET()).key(folderPrefix + foto), ResponseTransformer.toInputStream());

            return IoUtils.toByteArray(is);

        } catch (S3Exception e) {
            log.info("File not found on bucket: {}", foto);

            try {
                InputStream defaultImage = new ClassPathResource("images/no_image.png").getInputStream();
                return IoUtils.toByteArray(defaultImage);
            } catch (IOException ex) {
                log.error("Error processing default image: {}", ex.getMessage());
            }
        } catch (IOException e) {
            log.error("Error processing file: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public String excluir(String foto) {
//        amazonS3.deleteObjects(new DeleteObjectsRequest(BUCKET).withKeys(foto, THUMBNAIL_PREFIX + foto));

        try {
            S3Client s3 = s3Config.getS3();

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(s3Config.getBUCKET())
                    .key("%s%s".formatted(folderPrefix, foto))
                    .build();

            s3Config.getS3().deleteObject(deleteObjectRequest);
            System.out.println("File deleted: " + foto);
        } catch (S3Exception e) {
            log.error("Error deleting file: {}", foto);
            return "Error deleting file: %s".formatted(foto);
        }

        return "File deleted: %s".formatted(foto);
    }

//    @Override
//    public String getUrl(String foto) {
//        if (!StringUtils.isEmpty(foto)) {
//            return "https://s3-sa-east-1.amazonaws.com/awbrewer/" + foto;
//        }
//
//        return null;
//    }

}
