package com.classicmodels.api.controller;

import com.classicmodels.domain.model.Customers;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.service.CustomerCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.http.HttpStatus;
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
        return customerCatalogService.buscarTodos();
    }

    @GetMapping("/{customerNumber}")
    public Customers listarPorId(@PathVariable Integer customerNumber) {
        return customerCatalogService.buscarPorId(customerNumber);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customers adicionar(@Valid @RequestBody Customers customers) {
        return customerCatalogService.salvar(customers);
    }

}
