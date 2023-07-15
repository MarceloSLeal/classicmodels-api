package com.classicmodels.domain.service;

import com.classicmodels.domain.exception.BusinessException;
import com.classicmodels.domain.model.Employee;
import com.classicmodels.domain.repository.EmployeesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class EmployeesCatalogService {

    private EmployeesRepository employeesRepository;

    public List<Employee> buscarTodos() {
        return employeesRepository.findAll();
    }

    public Employee buscarPorId(Long id) {
        return employeesRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Employee not found"));
    }

    @Transactional
    public Employee salvar(Employee employee) {

        return employeesRepository.save(employee);

    }

}
