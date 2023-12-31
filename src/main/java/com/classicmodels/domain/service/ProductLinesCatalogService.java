package com.classicmodels.domain.service;

import com.classicmodels.domain.model.ProductLines;
import com.classicmodels.domain.repository.ProductLinesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ProductLinesCatalogService {

    private ProductLinesRepository productLinesRepository;

    @Transactional
    public ProductLines salvar(ProductLines productLines) {

        return productLinesRepository.save(productLines);
    }

    @Transactional
    public void excluir(String productLine) {
        productLinesRepository.deleteById(productLine);
    }

}
