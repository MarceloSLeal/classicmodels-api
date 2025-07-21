package com.classicmodels.domain.model.charts;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class PieChart {

    @Id
    private String id;
    private String label;
    private Integer totalOrdered;

}
