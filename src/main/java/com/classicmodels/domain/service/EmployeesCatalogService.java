package com.classicmodels.domain.service;

import com.classicmodels.domain.exception.BusinessException;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Employees;
import com.classicmodels.domain.repository.EmployeesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class EmployeesCatalogService {

    private EmployeesRepository employeesRepository;

    public List<Employees> buscarTodos() {
        return employeesRepository.findAll();
    }

    public Employees buscarPorId(Integer employeeNumber) {
        return employeesRepository.findById(employeeNumber)
                .orElseThrow(() -> new BusinessException("Employee not found"));
    }

    @Transactional
    public Employees salvar(Employees employees) {

        return employeesRepository.save(employees);

    }

}
