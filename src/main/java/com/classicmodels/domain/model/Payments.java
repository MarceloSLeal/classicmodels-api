package com.classicmodels.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payments {

    @NotNull
    private Long customerId;

    @Id
    @GeneratedValue
    @JdbcTypeCode(Types.VARCHAR)
    private UUID checkNumber;

    @FutureOrPresent
    private OffsetDateTime paymentDate;

    @Digits(integer = 10, fraction = 2, message = "max 10 digits and 2 after the .")
    private Double amount;

}
