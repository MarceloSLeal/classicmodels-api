package com.classicmodels.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {

    @ManyToOne()
    @JoinColumn(name = "officeId")
    private Offices offices;

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
    private Long reportsTo;
    private String jobTitle;

}
