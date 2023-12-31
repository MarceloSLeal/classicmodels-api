package com.classicmodels.api.mapper;

import com.classicmodels.api.model.ProductLinesRepModel;
import com.classicmodels.api.model.input.ProductLinesInput;
import com.classicmodels.domain.model.ProductLines;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ProductLinesMapper {

    private ModelMapper modelMapper;

    public ProductLinesRepModel toModel(ProductLines productLines) {
        return modelMapper.map(productLines, ProductLinesRepModel.class);
    }

    public List<ProductLinesRepModel> toCollectionModel(List<ProductLines> productLines) {
        return productLines.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public ProductLines toEntity(ProductLinesInput productLinesInput) {
        return modelMapper.map(productLinesInput, ProductLines.class);
    }

}
