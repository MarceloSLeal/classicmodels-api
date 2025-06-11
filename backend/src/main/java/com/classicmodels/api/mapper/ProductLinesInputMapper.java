package com.classicmodels.api.mapper;

import com.classicmodels.api.model.input.ProductLinesInput;
import com.classicmodels.domain.model.ProductLines;
import org.springframework.stereotype.Component;

@Component
public class ProductLinesInputMapper {

    public ProductLines toEntity(ProductLinesInput productLinesInput) {

        String aux = "";
        String extension;
        String fileName = null;

        if (productLinesInput.getImage() != null) {
            aux = productLinesInput.getImage().getOriginalFilename();
        }

        assert aux != null;
        if ( aux.contains(".")) {
            extension = aux.substring(aux.lastIndexOf(".") + 1);
            fileName = String.format("%s.%s", productLinesInput.getProductLine(), extension);
        }

        return new ProductLines(
                productLinesInput.getProductLine(),
                productLinesInput.getTextDescription(),
                productLinesInput.getHtmlDescription(),
                fileName
        );
    }

}