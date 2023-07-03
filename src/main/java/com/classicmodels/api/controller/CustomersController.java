package com.classicmodels.api.controller;

import com.classicmodels.domain.model.Customer;
import com.classicmodels.domain.repository.CustomerRepository;
import com.classicmodels.domain.service.CustomerCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerRepository customerRepository;
    private CustomerCatalogService customerCatalogService;

    @GetMapping
    public List<Customer> listar() {
        return customerCatalogService.buscarTodos();
    }

    @GetMapping("/{customerNumber}")
    public Customer listarPorId(@PathVariable Integer customerNumber) {
        return customerCatalogService.buscarPorId(customerNumber);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer adicionar(@Valid @RequestBody Customer customer) {
        return customerCatalogService.salvar(customer);
    }

}
