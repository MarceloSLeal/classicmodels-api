package com.classicmodels.api.controller;

import com.classicmodels.domain.model.Payments;
import com.classicmodels.domain.repository.PaymentsRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private PaymentsRepository paymentsRepository;

    @GetMapping
    public List<Payments> listar() {
        return paymentsRepository.findAll();
    }

}
