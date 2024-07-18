package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.OfficesMapper;
import com.classicmodels.api.model.OfficesRepModel;
import com.classicmodels.api.model.input.OfficesInput;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Offices;
import com.classicmodels.domain.repository.OfficesRepository;
import com.classicmodels.domain.service.OfficesCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/offices")
@CrossOrigin(origins = "${CONTROLLERS_CROSS_ORIGIN}")
public class OfficesController {

    private OfficesRepository officesRepository;
    private OfficesMapper officesMapper;
    private OfficesCatalogService officesCatalogService;

    @GetMapping
    public List<OfficesRepModel> listar() {
        return officesMapper.toCollectionModel(officesRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficesRepModel> buscaPorId(@PathVariable Long id) {

        return officesRepository.findById(id)
                .map(offices -> ResponseEntity.ok(officesMapper.toModel(offices)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OfficesRepModel adicionar(@Valid @RequestBody OfficesInput officesInput) {

        Offices newOffices = officesMapper.toEntity(officesInput);

        Offices savedOffices = officesCatalogService.salvar(newOffices);
        return officesMapper.toModel(savedOffices);
    }

    @GetMapping("/officeslist")
    public ResponseEntity<List<Long>> buscarPorOfficeIds() {

        List<Long> officeIds = officesRepository.findIds();
        if (officeIds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(officeIds);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficesRepModel> atualizar(@PathVariable Long id, @Valid @RequestBody OfficesInput officesInput) {

        officesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Office not exists"));

        Offices editedOffice = officesMapper.toEntity(officesInput);
        editedOffice.setId(id);

        Offices savedOffices = officesCatalogService.salvar(editedOffice);
        return ResponseEntity.ok(officesMapper.toModel(savedOffices));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        if (!officesRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        officesCatalogService.excluir(id);

        return ResponseEntity.noContent().build();
    }

}
