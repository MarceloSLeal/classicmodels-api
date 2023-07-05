package com.classicmodels.domain.service;

import com.classicmodels.domain.exception.BusinessException;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Customers;
import com.classicmodels.domain.model.Employees;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.repository.EmployeesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerCatalogService {

    private CustomersRepository customersRepository;
    private EmployeesRepository employeesRepository;

    @Transactional
    public Customers salvar(Customers customers) {

        Integer employeeNumber = customers.getEmployees().getEmployeeNumber();
        boolean customerEmail = customersRepository.findByCustomerEmail(customers.getCustomerEmail())
                .stream()
                .anyMatch(customerExists -> !customerExists.equals(customers));

        if (employeeNumber == null) {
            return customersRepository.save(customers);
        } else if (employeeNumber != null) {
            Optional<Employees> employees = Optional.ofNullable(employeesRepository.findById(employeeNumber)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found")));
        }

        if (customerEmail) {
            throw new BusinessException("There is already a customer registered with this email");
        }

        return customersRepository.save(customers);
    }

    @Transactional
    public void excluir(Integer customerNumber) {

        customersRepository.deleteById(customerNumber);

    }


}
