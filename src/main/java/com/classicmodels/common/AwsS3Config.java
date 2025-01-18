package com.classicmodels.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsS3Config {

    @Getter
    @Value("${BUCKET}")
    private String BUCKET;

    @Value("${ACCESS_KEY}")
    private String ACCESSKEY;

    @Value("${SECRET_KEY}")
    private String SECRETKEY;

    @Value("${REGION_NAME}")
    private String REGIONNAME;

    @Bean
    public S3Client getS3() {
        AwsCredentials credentials = AwsBasicCredentials.create(ACCESSKEY, SECRETKEY);
        return S3Client.builder()
                .region(Region.of(REGIONNAME))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

}