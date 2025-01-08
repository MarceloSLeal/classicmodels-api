package com.classicmodels.domain.service;

import com.classicmodels.domain.model.ProductLines;
import com.classicmodels.domain.repository.ProductLinesRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ProductLinesCatalogService {

    @Autowired
    private ProductLinesRepository productLinesRepository;

    @Transactional
    public ProductLines salvar(ProductLines productLines) {
        try {
            return productLinesRepository.save(productLines);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    public void excluir(String productLine) {
        productLinesRepository.deleteById(productLine);
    }

}
