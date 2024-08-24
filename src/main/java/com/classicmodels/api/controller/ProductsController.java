package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.ProductsMapper;
import com.classicmodels.api.model.ProductsRepModel;
import com.classicmodels.api.model.input.ProductsInput;
import com.classicmodels.api.model.lists.ProductsRepModelIdNameMsrpList;
import com.classicmodels.api.model.lists.interfaces.ProductsIdNameMsrpProjection;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Products;
import com.classicmodels.domain.repository.ProductsRepository;
import com.classicmodels.domain.service.ProductsCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductsController {

    private ProductsRepository productsRepository;
    private ProductsMapper productsMapper;
    private ProductsCatalogService productsCatalogService;

    @GetMapping
    public List<Products> listar() {
        return productsRepository.findAll();
    }

    @GetMapping("/idnamemsrp")
    public ResponseEntity<List<ProductsRepModelIdNameMsrpList>> buscarPorIdNameMsrp() {

        List<ProductsIdNameMsrpProjection> projections = productsRepository.findIdNameMsrp();
        if (projections.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ProductsRepModelIdNameMsrpList> productsIds = projections.stream()
                .map(projection -> {
                    ProductsRepModelIdNameMsrpList dto = new ProductsRepModelIdNameMsrpList();
                    dto.setId(projection.getId());
                    dto.setName(projection.getName());
                    dto.setMsrp(projection.getMsrp());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(productsIds);
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

        List<Products> products = productsRepository.findByProductLine(productLine);

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

    @PostMapping
    public ResponseEntity<ProductsRepModel> adicionar(@Valid @RequestBody ProductsInput productsInput) {

        productsRepository.findByName(productsInput.getName())
                .ifPresent(product -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "This product name %s already exists".formatted(productsInput.getName()));
                });

        Products products = productsMapper.toEntity(productsInput);
        Products savedProduct = productsCatalogService.salvar(products);
        ProductsRepModel productsRepModel = productsMapper.toModel(savedProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(productsRepModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductsRepModel> atualizar(@PathVariable Long id, @Valid @RequestBody ProductsInput productsInput) {

        productsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("This product id %s doesn't exit".formatted(id)));

        Products products = productsMapper.toEntity(productsInput);
        products.setId(id);

        return ResponseEntity.ok(productsMapper
                .toModel(productsCatalogService.salvar(products)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        if (!productsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        productsCatalogService.excluir(id);

        return ResponseEntity.noContent().build();
    }

}
