package com.classicmodels.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "productlines")
public class ProductLines {

    @Id
    @Size(max = 50)
    @Column(name = "product_line")
    private String productLine;

    @Size(max = 4000)
    private String textDescription;

    private String htmlDescription;

    private MultipartFile image;

}
