package com.classicmodels.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payments {

    @EqualsAndHashCode.Include
    @NotNull
    private Long orderId;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    @JdbcTypeCode(Types.VARCHAR)
    private UUID checkNumber;

    @FutureOrPresent
    private OffsetDateTime paymentDate;

    @Digits(integer = 10, fraction = 2, message = "max 10 digits and 2 after the .")
    private Double amount;

}
