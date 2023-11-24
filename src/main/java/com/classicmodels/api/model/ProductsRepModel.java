package com.classicmodels.api.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductsRepModel {

    private Long id;
    private String name;
    private String productLine;
    private String scale;
    private String vendor;
    private String description;
    private Integer quantityInStock;
    private Double buyPrice;
    private Double msrp;

}
