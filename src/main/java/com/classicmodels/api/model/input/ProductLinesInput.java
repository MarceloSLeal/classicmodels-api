package com.classicmodels.api.model.input;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductLinesInput {

    @NotBlank(message = "Product line cannot be blank")
    @Size(max = 50, message = "Product line must have at most {max} characters")
    private String productLine;

    @Size(max = 4000, message = "Text description must have at most {max} characters")
    private String textDescription;

    private String htmlDescription;

    @Nullable
    private MultipartFile image;

}