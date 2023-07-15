package com.classicmodels.api.controller;

import com.classicmodels.domain.model.Employee;
import com.classicmodels.domain.repository.EmployeesRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private EmployeesRepository employeesRepository;

    @GetMapping
    public List<Employee> listar() {
        return employeesRepository.findAll();
    }

}
