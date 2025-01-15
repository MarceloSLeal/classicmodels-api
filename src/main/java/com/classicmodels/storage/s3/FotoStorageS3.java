package com.classicmodels.storage.s3;

import com.classicmodels.common.AwsS3Config;
import com.classicmodels.storage.FotoStorage;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.utils.IoUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

    private void enviarFoto(String nome, MultipartFile arquivo) throws IOException{

        String aux = arquivo.getOriginalFilename();
        extension = "";

        if (aux != null && aux.contains(".")) {
            extension = aux.substring(aux.lastIndexOf(".") + 1);
        }

        Map<String, String> metadata = new HashMap<>();

        metadata.put("Content Type", arquivo.getContentType());
        metadata.put("Size", String.valueOf(arquivo.getSize()));
        metadata.put("Environment", "Dev");
        metadata.put("Original Name", arquivo.getOriginalFilename());
        metadata.put("Extension", extension);

        PutObjectRequest por = PutObjectRequest.builder()
                .bucket(s3Config.getBUCKET())
                                .key("%s%s.%s".formatted(folderPrefix, nome, extension))
                                        .metadata(metadata)
                                                .build();

        InputStream inputStream = arquivo.getInputStream();
        s3Config.getS3().putObject(por, RequestBody.fromInputStream(inputStream, arquivo.getSize()));

    }

    @Override
    public byte[] recuperar(String foto) {
        try {
            InputStream is = s3Config.getS3().getObject(request ->
                    request.bucket(s3Config.getBUCKET()).key(folderPrefix + foto), ResponseTransformer.toInputStream());

            return IoUtils.toByteArray(is);

        } catch (S3Exception e) {
            //TODO - remvoer esses System.out depois
            System.out.println("Arquivo não encontrado no bucket: " + e.getMessage());

            try {
                InputStream defaultImage = new ClassPathResource("images/no_image.png").getInputStream();
                return IoUtils.toByteArray(defaultImage);
            } catch (IOException ex) {
                System.out.println("Erro ao processar o arquivo: " + ex.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Erro ao processar o arquivo: " + e.getMessage());
        }
        return null;
    }

//    @Override
//    public byte[] recuperar(String foto) {
//        InputStream is = s3Client.getObject(BUCKET, foto).getObjectContent();
//        try {
//            return IoUtils.toByteArray(is);
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

}
