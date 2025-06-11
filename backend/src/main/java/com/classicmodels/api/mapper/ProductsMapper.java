package com.classicmodels.api.mapper;

import com.classicmodels.api.model.ProductsRepModel;
import com.classicmodels.api.model.input.ProductsInput;
import com.classicmodels.domain.model.Products;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ProductsMapper {

    private ModelMapper modelMapper;

    public ProductsRepModel toModel(Products products) {
        return modelMapper.map(products, ProductsRepModel.class);
    }

    public List<ProductsRepModel> toCollectionModel(List<Products> products) {
        return products.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Products toEntity(ProductsInput productsInput) {
        return modelMapper.map(productsInput, Products.class);
    }
}
