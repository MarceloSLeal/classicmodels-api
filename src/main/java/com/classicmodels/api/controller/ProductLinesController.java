package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.ProductLinesInputMapper;
import com.classicmodels.api.mapper.ProductLinesMapper;
import com.classicmodels.api.model.ProductLinesRepModel;
import com.classicmodels.api.model.input.ProductLinesInput;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.ProductLines;
import com.classicmodels.domain.repository.ProductLinesRepository;
import com.classicmodels.domain.service.ProductLinesCatalogService;
import com.classicmodels.storage.FotoStorage;
import com.classicmodels.storage.FotoStorageDeleteRunnable;
import com.classicmodels.storage.FotoStorageSaveRunnable;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/productlines")
public class ProductLinesController {

    private ProductLinesRepository productLinesRepository;
    private ProductLinesMapper productLinesMapper;
    private ProductLinesInputMapper productLinesInputMapper;
    private ProductLinesCatalogService productLinesCatalogService;
    private FotoStorage fotoStorage;

    @GetMapping
    public ResponseEntity<List<ProductLinesRepModel>> listar() {
        List<ProductLines> productLines = productLinesRepository.findAll();

        List<ProductLinesRepModel> productLinesRepModelList = new java.util.ArrayList<>(List.of());

        for (ProductLines productLine : productLines) {

            productLinesRepModelList.add(new ProductLinesRepModel(
                    productLine.getProductLine(),
                    productLine.getTextDescription(),
                    productLine.getHtmlDescription(),
                    fotoStorage.recuperar(productLine.getImage())));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "public, max-age=300");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header(String.valueOf(headers))
                .body(productLinesRepModelList);
    }

    @GetMapping("/{productLine}")
    public ResponseEntity<ProductLinesRepModel> buscarPorProductLine(@PathVariable String productLine) {

        return productLinesRepository.findByProductLine(productLine)
                .map(productLines -> ResponseEntity.ok(productLinesMapper.toModel(productLines)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "This product line %s doesn't exist".formatted(productLine)));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> adicionar(@Valid @ModelAttribute ProductLinesInput productLinesInput) throws IOException {

        Map<String, String> response = new HashMap<>();

        productLinesRepository.findByProductLine(productLinesInput.getProductLine())
                .ifPresent(productLines -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "This product line %s already exists".formatted(productLinesInput.getProductLine()));
                });

        ProductLines productLines = productLinesInputMapper.toEntity(productLinesInput);

        try {
            productLinesCatalogService.salvar(productLines);
        } catch (Exception e) {
            response.put("message", "Error creating product line");
            return ResponseEntity.badRequest().body(response);
        }

        if (productLinesInput.getImage() != null) {
            if (!Objects.requireNonNull(productLinesInput.getImage().getContentType()).matches("image/png|image/jpeg")) {
                throw new IllegalArgumentException("Only PNG or JPEG images are allowed");
            }

            byte[] fileContent = productLinesInput.getImage().getBytes();

            Thread thread = new Thread(new FotoStorageSaveRunnable(fileContent, fotoStorage,
                    productLinesInput.getImage().getContentType(), productLinesInput.getProductLine()));

            thread.start();
        }

        response.put("message", "Product Line created successfully");
        return ResponseEntity.ok().body(response);
    }


    @PutMapping("/{productLine}")
    public ResponseEntity<Map<String, String>> atualizar(@PathVariable String productLine, @Valid @ModelAttribute ProductLinesInput productLinesInput)
            throws IOException {

        Map<String, String> response = new HashMap<>();

        ProductLines existingProductLines = productLinesRepository.findByProductLine(productLine)
                .orElseThrow(() -> new EntityNotFoundException("This product line %s doesn't exist".formatted(productLine)));

        ProductLines productLines = productLinesInputMapper.toEntity(productLinesInput);
        productLines.setImage(existingProductLines.getImage());

        try {
            productLinesCatalogService.salvar(productLines);
        } catch (Exception e) {
            response.put("message", "Error updating product line");
            return ResponseEntity.badRequest().body(response);
        }

        if (productLinesInput.getImage() != null) {
            if (!Objects.requireNonNull(productLinesInput.getImage().getContentType()).matches("image/png|image/jpeg")) {
                throw new IllegalArgumentException("Only PNG or JPEG images are allowed");
            }

            byte[] fileContent = productLinesInput.getImage().getBytes();

            Thread thread = new Thread(new FotoStorageSaveRunnable(fileContent, fotoStorage,
                    productLinesInput.getImage().getContentType(), productLinesInput.getProductLine()));

            thread.start();
        }

        response.put("message", "Product Line updated successfully");
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{productLine}")
    public ResponseEntity<Map<String, String>> excluir(@PathVariable String productLine) {

        Map<String, String> response = new HashMap<>();

        ProductLines existingProductLines = productLinesRepository.findByProductLine(productLine)
                .orElseThrow(() -> new EntityNotFoundException("This product line %s doesn't exist".formatted(productLine)));

        if (!productLinesRepository.existsById(productLine)) {
            response.put("message", "This product line %s doesn't exist".formatted(productLine));
            return ResponseEntity.badRequest().body(response);
        }

        try {
            productLinesCatalogService.excluir(productLine);
        } catch (Exception e) {
            response.put("message", "Error deleting product line");
            return ResponseEntity.badRequest().body(response);
        }

        if (existingProductLines.getImage() != null) {
            Thread thread = new Thread(new FotoStorageDeleteRunnable(existingProductLines.getImage(), fotoStorage));

            thread.start();
        }

        response.put("message", "Product Line deleted successfully");
        return ResponseEntity.ok().body(response);
    }

    private ProductLines getProductLines(ProductLinesInput productLinesInput) {
        String aux = null;
        if (productLinesInput.getImage() != null) {
            aux = productLinesInput.getImage().getOriginalFilename();
        }
        String extension = "";

        if (aux != null && aux.contains(".")) {
            extension = aux.substring(aux.lastIndexOf(".") + 1);
        }

        return new ProductLines(
                productLinesInput.getProductLine(),
                productLinesInput.getTextDescription(),
                productLinesInput.getHtmlDescription(),
                productLinesInput.getProductLine() + "." + extension
        );
    }
}