package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.OfficesMapper;
import com.classicmodels.api.model.OfficesRepModel;
import com.classicmodels.domain.model.Offices;
import com.classicmodels.domain.repository.OfficesRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/offices")
public class OfficesController {

    private OfficesRepository officesRepository;
    private OfficesMapper officesMapper;

    @GetMapping
    public List<OfficesRepModel> listar() {
        return officesMapper.toCollectionModel(officesRepository.findAll());
    }

}
