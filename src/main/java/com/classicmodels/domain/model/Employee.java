package com.classicmodels.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    private Offices officeId;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Customer> customers = new ArrayList<>();

    @Formula("(SELECT GROUP_CONCAT(c.id SEPARATOR ',') FROM customers c WHERE c.employee_id = id)")
    @Fetch(FetchMode.SELECT)
    private String customersId;

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
