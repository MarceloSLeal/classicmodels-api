package com.classicmodels.domain.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {

//    @ManyToOne()
//    @JoinColumn(name = "officeCode")
//    private Offices offices;

//    @ManyToOne
//    @JoinColumn(name = "reportsTo")
//    private Employees manager;

    @OneToMany(mappedBy = "employee")
    private List<Customer> customers = new ArrayList<>();

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastName;
    private String firstName;
    private String extension;
    private String email;

    @Column(insertable = false, updatable = false)
    private Integer officeId;

    private Integer reportsTo;
    private String jobTitle;


}