package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.ProductLinesMapper;
import com.classicmodels.api.model.ProductLinesRepModel;
import com.classicmodels.api.model.input.ProductLinesInput;
import com.classicmodels.domain.model.ProductLines;
import com.classicmodels.domain.repository.ProductLinesRepository;
import com.classicmodels.domain.service.ProductLinesCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/productlines")
public class ProductLinesController {

    private ProductLinesRepository productLinesRepository;
    private ProductLinesMapper productLinesMapper;
    private ProductLinesCatalogService productLinesCatalogService;

    @GetMapping
    public List<ProductLines> listar() {
        return productLinesRepository.findAll();
    }

    @GetMapping("/{productLine}")
    public ResponseEntity<ProductLinesRepModel> buscarPorProductLine(@PathVariable String productLine) {

        return productLinesRepository.findByProductLine(productLine)
                .map(productLines -> ResponseEntity.ok(productLinesMapper.toModel(productLines)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "This product line %s doesn't exist".formatted(productLine)));
    }

    @PostMapping
    public ResponseEntity<ProductLinesRepModel> adicionar(@Valid @RequestBody ProductLinesInput productLinesInput) {

        productLinesRepository.findByProductLine(productLinesInput.getProductLine())
                .ifPresent(productLines -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "This product line %s already exists".formatted(productLinesInput.getProductLine()));
                });

        ProductLines productLines = productLinesMapper.toEntity(productLinesInput);
        ProductLines savedProductLine = productLinesCatalogService.salvar(productLines);
        ProductLinesRepModel productLinesRepModel = productLinesMapper.toModel(savedProductLine);

        return ResponseEntity.status(HttpStatus.CREATED).body(productLinesRepModel);
    }

}
