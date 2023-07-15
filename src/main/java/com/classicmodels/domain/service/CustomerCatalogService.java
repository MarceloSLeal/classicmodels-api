package com.classicmodels.domain.service;

import com.classicmodels.domain.exception.BusinessException;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Customer;
import com.classicmodels.domain.model.Employee;
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
    public Customer salvar(Customer customer) {

        Long employeeNumber = customer.getEmployee().getId();
        boolean customerEmail = customersRepository.findByEmail(customer.getEmail())
                .stream()
                .anyMatch(customerExists -> !customerExists.equals(customer));

        if (employeeNumber == null) {
            return customersRepository.save(customer);
        } else if (employeeNumber != null) {
            Optional<Employee> employees = Optional.ofNullable(employeesRepository.findById(employeeNumber)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found")));
        }

        if (customerEmail) {
            throw new BusinessException("There is already a customer registered with this email");
        }

        return customersRepository.save(customer);
    }

    @Transactional
    public void excluir(Long customerNumber) {

        customersRepository.deleteById(customerNumber);

    }


}
