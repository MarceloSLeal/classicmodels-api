package com.classicmodels.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payments {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @NotBlank
    @Size(max = 50)
    private String checkNumber;

    @NotBlank
    @PastOrPresent
    private Date paymentDate;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "9999999999.99")
    private Double amount;

}
