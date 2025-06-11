package com.classicmodels.domain.service;

import com.classicmodels.domain.model.Products;
import com.classicmodels.domain.repository.ProductsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductsCatalogService {

    private ProductsRepository productsRepository;

    @Transactional
    public Products salvar(Products products) {

        return productsRepository.save(products);
    }

    @Transactional
    public void excluir(Long id) {
        productsRepository.deleteById(id);
    }

}
