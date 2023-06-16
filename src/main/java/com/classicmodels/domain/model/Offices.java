package com.classicmodels.domain.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "offices")
public class Offices {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer officeCode;
    private String city;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String state;
    private String country;
    private String postalCode;
    private String territory;

}
