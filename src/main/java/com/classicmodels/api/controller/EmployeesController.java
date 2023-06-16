package com.classicmodels.api.controller;

import com.classicmodels.domain.model.Employees;
import com.classicmodels.domain.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employees> listar() {
        return employeeRepository.findAll();
    }

}
