package com.classicmodels.api.controller;

import com.classicmodels.domain.exception.BusinessException;
import com.classicmodels.domain.model.Customers;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.service.CustomerCatalogService;
import com.classicmodels.domain.service.EmployeesCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomersController {

    private CustomersRepository customersRepository;
    private CustomerCatalogService customerCatalogService;

    private EmployeesCatalogService employeesCatalogService;

    @GetMapping
    public List<Customers> listar() {
        return customersRepository.findAll();
    }


    @GetMapping("/{customerNumber}")
    public ResponseEntity<Customers> buscarPorId(@PathVariable Integer customerNumber) {

        return customersRepository.findById(customerNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findbyemail/{customerEmail}")
    public ResponseEntity<Customers> buscarPorEmail(@PathVariable String customerEmail) {

        return customersRepository.findByCustomerEmail(customerEmail)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customers adicionar(@Valid @RequestBody Customers customers) {
        return customerCatalogService.salvar(customers);
    }

    @PutMapping("/{customerNumber}")
    public ResponseEntity<Customers> atualizar(@PathVariable Integer customerNumber, @Valid @RequestBody Customers customers) {

        boolean customerEmail = customersRepository.findByCustomerEmail(customers.getCustomerEmail()).isEmpty();

        if (!customersRepository.existsById(customerNumber)) {
            return ResponseEntity.notFound().build();
        }

        if (!customerEmail) {
            throw new BusinessException("There is already a customer registered with this email");
        }

        customers.setId(customerNumber);
        customers = customerCatalogService.salvar(customers);

        return ResponseEntity.ok(customers);
    }

    @DeleteMapping("/{customerNumber}")
    public ResponseEntity<Void> excluir(@PathVariable Integer customerNumber) {

        if(!customersRepository.existsById(customerNumber)) {
            return ResponseEntity.notFound().build();
        }

        customerCatalogService.excluir(customerNumber);

        return ResponseEntity.noContent().build();
    }

}
