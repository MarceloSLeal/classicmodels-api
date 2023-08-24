package com.classicmodels.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "payments")
@IdClass(PaymentsId.class)
public class Payments {

    @Id
    @NotBlank
    private Long customerId;

    @Id
    @NotBlank
    @Size(max = 50)
    private String checkNumber;

    @NotBlank
    @PastOrPresent
    private OffsetDateTime paymentDate;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "9999999999.99")
    private Double amount;

}
