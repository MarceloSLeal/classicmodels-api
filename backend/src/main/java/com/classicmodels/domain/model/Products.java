package com.classicmodels.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "products")
public class Products {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 70)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String productLine;

    @NotBlank
    @Size(max = 10)
    private String scale;

    @NotBlank
    @Size(max = 50)
    private String vendor;

    @NotBlank
    private String description;

    @Min(value = -32768, message = "Value must be greater or equal to -32768")
    @Max(value = 32768, message = "Value must be less than or equal to 32768")
    private Integer quantityInStock;

    @Digits(integer = 10, fraction = 2, message = "max 10 digits and 2 after the .")
    @Positive(message = "Value must be positive")
    private Double buyPrice;

    @Digits(integer = 10, fraction = 2, message = "max 10 digits and 2 after the .")
    @Positive(message = "Value must be positive")
    private Double msrp;

}
