package com.classicmodels.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomCustomer {

    private Integer id;
    private String name;
    private String email;
    private Integer customerId;

    public CustomCustomer(Integer id, String name, String email, Integer customerId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.customerId = customerId;
    }
}
