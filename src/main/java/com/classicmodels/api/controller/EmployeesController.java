package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.EmployeeMapper;
import com.classicmodels.api.model.EmployeeRepModel;
import com.classicmodels.api.model.input.EmployeeInput;
import com.classicmodels.domain.exception.BusinessException;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Employee;
import com.classicmodels.domain.repository.EmployeesRepository;
import com.classicmodels.domain.repository.OfficesRepository;
import com.classicmodels.domain.service.EmployeesCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private EmployeesRepository employeesRepository;
    private EmployeesCatalogService employeesCatalogService;
    private EmployeeMapper employeeMapper;
    private OfficesRepository officesRepository;

    @GetMapping
    public List<EmployeeRepModel> listar() {

        return employeeMapper.toCollectionModel(employeesRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeRepModel> buscarPorId(@PathVariable Long id) {

        return employeesRepository.findById(id)
                .map(employee -> ResponseEntity.ok(employeeMapper.toModel(employee)))
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/findbyemail/{email}")
    public ResponseEntity<EmployeeRepModel> buscarPorEmail(@PathVariable String email) {

        return employeesRepository.findByEmail(email)
                .map(employee -> ResponseEntity.ok(employeeMapper.toModel(employee)))
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeRepModel adicionar(@Valid @RequestBody EmployeeInput employeeInput) {

        Employee newEmployee = employeeMapper.toEntity(employeeInput);

        EmployeeRepModel employeeRepModel = employeeMapper.toModel(newEmployee);

        boolean employeeEmail = employeesRepository.findByEmail(newEmployee.getEmail())
                .stream()
                .anyMatch(employeeExists -> !employeeExists.equals(newEmployee));

        officesRepository.findById(Math.toIntExact(employeeInput.getOfficesId()))
                .orElseThrow(() -> new EntityNotFoundException("There is no office with that Id"));

        if (employeeEmail) {
            throw  new BusinessException("There is already a employee registered with this email");
        }

        employeesRepository.findById(employeeInput.getReportsTo())
                .orElseThrow(() -> new EntityNotFoundException("There is no employee with that id"));

        employeesCatalogService.salvar(newEmployee);

        return employeeRepModel;
    }

}
