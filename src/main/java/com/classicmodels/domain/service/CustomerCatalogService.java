package com.classicmodels.domain.service;

import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Customer;
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

        Long employeeId = customer.getEmployee() == null ? null : customer.getEmployee().getId();

        if (employeeId == null) {
            customersRepository.save(customer);
        } else {
            employeesRepository.findById(employeeId)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        }
       return customersRepository.save(customer);
    }

    @Transactional
    public void excluir(Long id) {
        customersRepository.deleteById(id);
    }
}
