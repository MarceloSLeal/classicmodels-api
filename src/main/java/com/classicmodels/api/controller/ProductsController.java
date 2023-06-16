package com.classicmodels.api.controller;

import com.classicmodels.domain.model.Products;
import com.classicmodels.domain.repository.ProductsRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductsController {

    private ProductsRepository productsRepository;

    @GetMapping
    public List<Products> listar() {
        return productsRepository.findAll();
    }

}
