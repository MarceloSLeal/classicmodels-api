package com.classicmodels.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "productlines")
public class ProductLines {

    public ProductLines() {
    }

    @Id
    @Size(max = 50)
    @Column(name = "product_line")
    private String productLine;

    @Size(max = 4000)
    private String textDescription;

    private String htmlDescription;

    private String image;

}
