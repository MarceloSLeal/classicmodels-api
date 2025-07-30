package com.classicmodels.domain.model.charts;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class BarChart {

    private String country;
    private Map<String, Integer> products = new LinkedHashMap<>();

    public BarChart(String country) {
        this.country = country;
    }

    public void addProduct(String product, Integer quantity) {
        products.put(product, quantity);
    }
}
