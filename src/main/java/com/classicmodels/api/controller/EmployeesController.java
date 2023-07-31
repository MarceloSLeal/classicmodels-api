package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.EmployeeMapper;
import com.classicmodels.api.model.EmployeeRepModel;
import com.classicmodels.domain.model.Employee;
import com.classicmodels.domain.repository.EmployeesRepository;
import com.classicmodels.domain.service.EmployeesCatalogService;
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
    private EmployeesCatalogService employeesCatalogService;
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<EmployeeRepModel> listar() {

        return employeeMapper.toCollectionModel(employeesRepository.findAll());
    }

}
