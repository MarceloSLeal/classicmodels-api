package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.ProductsMapper;
import com.classicmodels.api.model.ProductsRepModel;
import com.classicmodels.domain.model.Products;
import com.classicmodels.domain.repository.ProductsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductsController {

    private ProductsRepository productsRepository;
    private ProductsMapper productsMapper;

    @GetMapping
    public List<Products> listar() {
        return productsRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductsRepModel> buscarPorProductId(@PathVariable Long id) {

        return productsRepository.findById(id)
                .map(products -> ResponseEntity.ok(productsMapper.toModel(products)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "This product Id %s doesn't exist".formatted(id)));

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductsRepModel> buscarPorProducName(@PathVariable String name) {

        return productsRepository.findByName(name)
                .map(products -> ResponseEntity.ok(productsMapper.toModel(products)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "This product name %s doesn't exist".formatted(name)));
    }

    @GetMapping("/productline/{productLine}")
    public ResponseEntity<List<ProductsRepModel>> buscarPorProductLine(@PathVariable String productLine) {

        List<Products>  products = productsRepository.findByProductLine(productLine);

        if (!products.isEmpty()) {
            List<ProductsRepModel> productsRepModels = products.stream()
                    .map(productClass -> productsMapper.toModel(productClass))
                    .toList();
            return ResponseEntity.ok(productsRepModels);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This product line %s doesn't exist".formatted(productLine));
        }
    }

    @GetMapping("/scale/{scale}")
    public ResponseEntity<List<ProductsRepModel>> buscarPorProductScale(@PathVariable String scale) {

        List<Products> products = productsRepository.findByScale(scale);

        if (!products.isEmpty()) {
            List<ProductsRepModel> productsRepModels = products.stream()
                    .map(productClass -> productsMapper.toModel(productClass))
                    .toList();
            return ResponseEntity.ok(productsRepModels);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This product scale %s doesn't exist".formatted(scale));
        }
    }

    @GetMapping("/vendor/{vendor}")
    public ResponseEntity<List<ProductsRepModel>> buscarPorVendor(@PathVariable String vendor) {

        List<Products> products = productsRepository.findByVendor(vendor);

        if (!products.isEmpty()) {
            List<ProductsRepModel> productsRepModels = products.stream()
                    .map(productClass -> productsMapper.toModel(productClass))
                    .toList();
            return ResponseEntity.ok(productsRepModels);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This product vendor %s doesn't exist".formatted(vendor));
        }
    }


}
