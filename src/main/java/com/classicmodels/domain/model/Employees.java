package com.classicmodels.domain.model;

import com.classicmodels.domain.service.CustomerCatalogService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employees {

//    @ManyToOne()
//    @JoinColumn(name = "officeCode")
//    private Offices offices;

//    @ManyToOne
//    @JoinColumn(name = "reportsTo")
//    private Employees manager;

    @OneToMany(mappedBy = "employees")
    private List<Customers> customers = new ArrayList<>();

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String lastName;
    private String firstName;
    private String extension;
    private String email;

    @Column(insertable = false, updatable = false)
    private Integer officeId;

    private Integer reportsTo;
    private String jobTitle;


}
