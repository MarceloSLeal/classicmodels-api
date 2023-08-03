package com.classicmodels.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    //Não é mais necessário se o objetivo for apenas mostrar o Id de Offices
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officeId", nullable = false)
    @JsonIgnore
    private Offices offices;

    @Formula("(SELECT e.office_Id FROM employees e WHERE e.id = id)")
    @Fetch(FetchMode.SELECT)
    private Long officeId;

    //Não é mais necessário se o objetivo for apenas mostrar o Id de Offices
    @OneToMany(mappedBy = "employee")
    private List<Customer> customers = new ArrayList<>();

    ////
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
