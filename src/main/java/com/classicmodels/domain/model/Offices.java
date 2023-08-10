package com.classicmodels.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "offices")
public class Offices {

    @OneToMany(mappedBy = "offices")
    private List<Employee> employee;

    @Formula("(SELECT GROUP_CONCAT(e.id SEPARATOR ',') FROM employees e WHERE e.office_id = id)")
    @Fetch(FetchMode.SELECT)
    private String employeeId;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    private String city;

    @NotNull
    @Size(max = 50)
    private String phone;

    @NotNull
    @Size(max = 50)
    private String addressLine1;

    @Size(max = 50)
    private String addressLine2;

    @Size(max = 50)
    private String state;

    @NotNull
    @Size(max = 50)
    private String country;

    @NotNull
    @Size(max = 15)
    private String postalCode;

    @NotNull
    @Size(max = 10)
    private String territory;

}
