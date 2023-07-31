package com.classicmodels.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officeId", nullable = false)
    private Offices offices;

    @OneToMany(mappedBy = "employee")
    private List<Customer> customers = new ArrayList<>();

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 10)
    private String extension;

    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @Digits(integer = 10, fraction = 0, message = "Invalid integer value")
    private Long reportsTo;

    @NotBlank
    @Size(max = 50)
    private String jobTitle;

}
