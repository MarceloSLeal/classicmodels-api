package com.classicmodels.domain.service;

import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Customers;
import com.classicmodels.domain.model.Employees;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.repository.EmployeesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerCatalogService {

    private CustomersRepository customersRepository;
    private EmployeesRepository employeesRepository;

    public List<Customers> buscarTodos() {
        return customersRepository.findAll();
    }

    public Customers buscarPorId(Integer customerNumber) {
        return customersRepository.findById(customerNumber)
                .orElseThrow(() -> new EntityNotFoundException("Customer not Found"));
    }

    public Customers buscarPorEmail(String customerEmail) {
        Customers customers = new Customers();
        customers = customersRepository.findByCustomerEmail(customerEmail);
        return  customers;
    }

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

}
