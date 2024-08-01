package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.OfficesMapper;
import com.classicmodels.api.model.OfficesRepModel;
import com.classicmodels.api.model.input.OfficesInput;
import com.classicmodels.api.model.lists.OfficesRepModelIdCityList;
import com.classicmodels.api.model.lists.interfaces.OfficesIdCityProjection;
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
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/offices")
//@CrossOrigin(origins =  "http://localhost:5173", allowedHeaders = "*")
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

    @GetMapping("/idcity")
    public ResponseEntity<List<OfficesRepModelIdCityList>> buscarPorIdCity() {

        List<OfficesIdCityProjection> projections = officesRepository.findIdCity();
        if (projections.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<OfficesRepModelIdCityList> officesIdCity = projections.stream()
                .map(projection -> {
                    OfficesRepModelIdCityList dto = new OfficesRepModelIdCityList();
                    dto.setId(projection.getId());
                    dto.setCity(projection.getCity());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(officesIdCity);
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
