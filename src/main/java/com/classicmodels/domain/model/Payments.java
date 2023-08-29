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
    @NotNull
    private Long customerId;

    @Id
    @NotBlank
    @Size(max = 50)
    private String checkNumber;

    @NotNull
    @PastOrPresent
    private OffsetDateTime paymentDate;

    @Digits(integer = 10, fraction = 2, message = "max 10 digits and 2 after the .")
    private Double amount;

}
