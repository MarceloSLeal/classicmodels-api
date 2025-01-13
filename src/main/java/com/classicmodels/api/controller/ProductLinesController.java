package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.ProductLinesMapper;
import com.classicmodels.api.model.ProductLinesRepModel;
import com.classicmodels.api.model.input.ProductLinesInput;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.ProductLines;
import com.classicmodels.domain.repository.ProductLinesRepository;
import com.classicmodels.domain.service.ProductLinesCatalogService;
import com.classicmodels.storage.FotoStorage;
import com.classicmodels.storage.FotoStorageRunnable;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/productlines")
public class ProductLinesController {

    private ProductLinesRepository productLinesRepository;
    private ProductLinesMapper productLinesMapper;
    private ProductLinesCatalogService productLinesCatalogService;
    private FotoStorage fotoStorage;

    @GetMapping
    public List<ProductLines> listar() {
        return productLinesRepository.findAll();
    }

    @GetMapping("/teste2")
    public ResponseEntity<List<ProductLinesRepModel>> listar2() {
        List<ProductLines> productLines = productLinesRepository.findAll();

        List<ProductLinesRepModel> productLinesRepModelList = new java.util.ArrayList<>(List.of());

        for (ProductLines productLine : productLines) {

            String imageBase64 = Base64.getEncoder().encodeToString(fotoStorage.recuperar(productLine.getImage()));

            productLinesRepModelList.add(new ProductLinesRepModel(
                    productLine.getProductLine(),
                    productLine.getTextDescription(),
                    productLine.getHtmlDescription(),
//                    fotoStorage.recuperar(productLine.getImage())));
                    imageBase64.getBytes()));
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productLinesRepModelList);
    }

    @GetMapping("/teste/{foto}")
    public ResponseEntity<byte[]> teste(@PathVariable String foto) {
        byte[] fileContent = fotoStorage.recuperar(foto);

        if (fileContent == null) {
            return ResponseEntity.notFound().build();
        }

        String contentType = setContentType(foto);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + foto + "\"")
                .body(fileContent);
    }

    private String setContentType(String foto) {
        if (foto.endsWith(".jpg") || (foto.endsWith(".jpeg"))) {
            return "image/jpeg";
        } else if (foto.endsWith(".png")) {
            return "image/png";
        }
        return "application/octet-stream";
    }

    @GetMapping("/{productLine}")
    public ResponseEntity<ProductLinesRepModel> buscarPorProductLine(@PathVariable String productLine) {

        return productLinesRepository.findByProductLine(productLine)
                .map(productLines -> ResponseEntity.ok(productLinesMapper.toModel(productLines)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "This product line %s doesn't exist".formatted(productLine)));
    }

    @PostMapping
    public ResponseEntity<ProductLinesRepModel> adicionar(@Valid @ModelAttribute ProductLinesInput productLinesInput) {

        productLinesRepository.findByProductLine(productLinesInput.getProductLine())
                .ifPresent(productLines -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "This product line %s already exists".formatted(productLinesInput.getProductLine()));
                });

        final ProductLines productLines = getProductLines(productLinesInput);

        ProductLines savedProductLine = productLinesCatalogService.salvar(productLines);
        ProductLinesRepModel productLinesRepModel = productLinesMapper.toModel(savedProductLine);

        if (productLinesInput.getImage() != null) {
            Thread thread = new Thread(new FotoStorageRunnable(productLinesInput.getImage(), fotoStorage, productLinesInput.getProductLine()));
            thread.start();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(productLinesRepModel);
    }


    @PutMapping("/{productLine}")
    public ResponseEntity<ProductLinesRepModel> atualizar(@PathVariable String productLine, @Valid @RequestBody ProductLinesInput productLinesInput) {

        ProductLines existingProductLines = productLinesRepository.findByProductLine(productLine)
                .orElseThrow(() -> new EntityNotFoundException("This product line %s doesn't exist".formatted(productLine)));

        existingProductLines.setTextDescription(productLinesInput.getTextDescription());
        existingProductLines.setHtmlDescription(productLinesInput.getHtmlDescription());
        //TODO -- mudar o PUT para imagem
//        existingProductLines.setImage(productLinesInput.getImage());

        ProductLines savedProductLines = productLinesRepository.save(existingProductLines);

        return ResponseEntity.ok(productLinesMapper.toModel(savedProductLines));
    }

    @DeleteMapping("/{productLine}")
    public ResponseEntity<Void> excluir(@PathVariable String productLine) {

        if (!productLinesRepository.existsById(productLine)) {
            return ResponseEntity.badRequest().build();
        }

        productLinesCatalogService.excluir(productLine);

        return ResponseEntity.noContent().build();
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
