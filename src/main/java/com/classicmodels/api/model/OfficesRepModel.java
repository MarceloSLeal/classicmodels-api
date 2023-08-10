package com.classicmodels.api.model;

import com.classicmodels.domain.model.Offices;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OfficesRepModel {

    private List<Long> employeeId;
    private Long id;
    private String city;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String state;
    private String country;
    private String postalCode;
    private String territory;

    public OfficesRepModel() {
    }

    public OfficesRepModel(Offices offices) {

        if (offices.getEmployeeId() != null) {
            this.employeeId = Arrays.stream(offices.getEmployeeId().split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        } else {
            this.employeeId = new ArrayList<>();
        }

        this.id = offices.getId();
        this.city = offices.getCity();
        this.phone = offices.getPhone();
        this.addressLine1 = offices.getAddressLine1();
        this.addressLine2 = offices.getAddressLine2();
        this.state = offices.getState();
        this.country = offices.getCountry();
        this.postalCode = offices.getPostalCode();
        this.territory = offices.getTerritory();

    }
}
