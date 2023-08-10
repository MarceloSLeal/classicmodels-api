package com.classicmodels.domain.service;

import com.classicmodels.domain.model.Offices;
import com.classicmodels.domain.repository.OfficesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class OfficesCatalogService {

    private OfficesRepository officesRepository;

    @Transactional
    public Offices salvar(Offices offices) {

        return officesRepository.save(offices);
    }

    @Transactional
    public void excluir(Long id) {
        officesRepository.deleteById(id);
    }

}
