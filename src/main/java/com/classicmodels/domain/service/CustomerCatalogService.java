package com.classicmodels.domain.service;

import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Customers;
import com.classicmodels.domain.model.Employees;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.repository.EmployeesRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerCatalogService {

    private CustomersRepository customersRepository;
    private EmployeesRepository employeesRepository;

    @Transactional
    public Customers salvar(Customers customers) {

        Integer employeeNumber = customers.getEmployees().getEmployeeNumber();

        if (employeeNumber == null) {
            return customersRepository.save(customers);
        } else if (employeeNumber != null) {
            Optional<Employees> employees = Optional.ofNullable(employeesRepository.findById(employeeNumber)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found")));

            return customersRepository.save(customers);
        }

        return null;
    }

    @Transactional
    public ResponseEntity<Void> excluir(Integer customerNumber) {

        if (!customersRepository.existsById(customerNumber)) {
            return ResponseEntity.notFound().build();
        }

        customersRepository.deleteById(customerNumber);

        return ResponseEntity.noContent().build();
    }


}
