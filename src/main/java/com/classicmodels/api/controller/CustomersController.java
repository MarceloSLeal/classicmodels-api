package com.classicmodels.api.controller;

import com.classicmodels.domain.model.Customers;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.service.CustomerCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomersController {

    private CustomersRepository customersRepository;
    private CustomerCatalogService customerCatalogService;

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

        if (!customersRepository.existsById(customerNumber)) {
            return ResponseEntity.notFound().build();
        }

        customers.setCustomerNumber(customerNumber);
        customerCatalogService.salvar(customers);

        return ResponseEntity.ok(customers);
    }

    @DeleteMapping("/{customerNumber}")
    public void excluir(@PathVariable Integer customerNumber) {
        customerCatalogService.excluir(customerNumber);
    }

}
