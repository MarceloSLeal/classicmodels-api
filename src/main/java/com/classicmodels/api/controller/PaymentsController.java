package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.PaymentsMapper;
import com.classicmodels.api.model.PaymentsRepModel;
import com.classicmodels.api.model.input.PaymentsInput;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Payments;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.repository.PaymentsRepository;
import com.classicmodels.domain.service.PaymentsCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private PaymentsRepository paymentsRepository;
    private PaymentsMapper paymentsMapper;
    private PaymentsCatalogService paymentsCatalogService;
    private CustomersRepository customersRepository;

    @GetMapping
    public List<Payments> listar() {
        return paymentsRepository.findAll();
    }

    @GetMapping("/customerid/{id}")
    public ResponseEntity<List<PaymentsRepModel>> buscarPorCustomerId(@PathVariable Long id) {

        List<Payments> payments = paymentsRepository.findByCustomerId(id);

        if (!payments.isEmpty()) {
            List<PaymentsRepModel> paymentsRepModels = payments.stream()
                    .map(paymentsClass -> paymentsMapper.toModel(paymentsClass))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(paymentsRepModels);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/checknumber/{checkNumber}")
    public ResponseEntity<PaymentsRepModel> buscarPorCheckNumber(@PathVariable String checkNumber) {

        UUID uuid = UUID.fromString(String.valueOf(checkNumber));

        return paymentsRepository.findByCheckNumber(uuid)
                .map(payments -> ResponseEntity.ok(paymentsMapper.toModel(payments)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentsRepModel adicionar(@Valid @RequestBody PaymentsInput paymentsInput) {

        customersRepository.findById(paymentsInput.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("There is no customer with that Id"));

        Payments payments = paymentsMapper.toEntity(paymentsInput);

        return paymentsMapper.toModel(paymentsCatalogService.salvar(payments));
    }

}