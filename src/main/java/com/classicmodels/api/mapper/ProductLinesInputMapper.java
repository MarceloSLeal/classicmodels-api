package com.classicmodels.api.mapper;

import com.classicmodels.api.model.input.ProductLinesInput;
import com.classicmodels.domain.model.ProductLines;
import org.springframework.stereotype.Component;

@Component
public class ProductLinesInputMapper {

    public ProductLines toEntity(ProductLinesInput productLinesInput) {

        String aux = null;
        String extension = null;

        if (productLinesInput.getImage() != null) {
            aux = productLinesInput.getImage().getOriginalFilename();
        }

        if ( aux != null && aux.contains(".")) {
            extension = aux.substring(aux.lastIndexOf(".") + 1);
        }

        return new ProductLines(
                productLinesInput.getProductLine(),
                productLinesInput.getTextDescription(),
                productLinesInput.getHtmlDescription(),
                String.format("%s.%s", productLinesInput.getProductLine(), extension)
        );
    }

}