package com.classicmodels.domain.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Date;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Orders {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime date;
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime requiredDate;
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime shippedDate;
    private String status;
    private String comments;
    private Integer customerId;

}
