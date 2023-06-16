package com.classicmodels.domain.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
    private Integer orderNumber;
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    @Temporal(TemporalType.DATE)
    private Date requiredDate;
    @Temporal(TemporalType.DATE)
    private Date shippedDate;
    private String status;
    private String comments;
    private Integer customerNumber;

}
