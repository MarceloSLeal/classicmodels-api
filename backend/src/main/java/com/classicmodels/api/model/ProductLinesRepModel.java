package com.classicmodels.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductLinesRepModel {

    private String productLine;
    private String textDescription;
    private String htmlDescription;
    private byte[] image;

    public ProductLinesRepModel() {
    }

    public ProductLinesRepModel(String productLine, String textDescription, String htmlDescription, byte[] image) {
        this.productLine = productLine;
        this.textDescription = textDescription;
        this.htmlDescription = htmlDescription;
        this.image = image;
    }

}