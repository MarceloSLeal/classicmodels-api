package com.classicmodels.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductLinesRepModel {

    private String productLine;
    private String textDescription;
    private String htmlDescription;
//    private String image;

}
