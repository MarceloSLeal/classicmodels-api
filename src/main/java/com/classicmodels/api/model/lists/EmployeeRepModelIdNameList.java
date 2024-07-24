package com.classicmodels.api.model.lists;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class EmployeeRepModelIdNameList {

    private Long id;
    private String lastName;
    private String firstName;
    private String jobTitle;

}
