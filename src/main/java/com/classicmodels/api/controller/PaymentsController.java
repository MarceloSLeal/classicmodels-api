package com.classicmodels.api.controller;

import ch.qos.logback.classic.pattern.DateConverter;
import com.classicmodels.api.mapper.PaymentsMapper;
import com.classicmodels.api.model.PaymentsRepModel;
import com.classicmodels.api.model.input.PaymentsInput;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Payments;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.repository.PaymentsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private PaymentsRepository paymentsRepository;
    private PaymentsMapper paymentsMapper;
    private CustomersRepository customersRepository;
    private ObjectMapper objectMapper;
    private ModelMapper modelMapper;

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
    public ResponseEntity<List<PaymentsRepModel>> buscarPorCheckNumber(@PathVariable String checkNumber) {

        List<Payments> payments = paymentsRepository.findByCheckNumber(checkNumber);

        if (!payments.isEmpty()) {
            List<PaymentsRepModel> paymentsRepModels = payments.stream()
                    .map(paymentsClass -> paymentsMapper.toModel(paymentsClass))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(paymentsRepModels);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{customerId}/{checkNumber}")
    public ResponseEntity<PaymentsRepModel> buscarPorChaveComposta(@PathVariable Long customerId,
                                                                   @PathVariable String checkNumber) {
        Optional<Payments> payments = paymentsRepository.findByCustomerIdAndCheckNumber(customerId, checkNumber);

        if (payments.isPresent()) {
            Payments p1 = payments.get();
            PaymentsRepModel paymentsRepModel = paymentsMapper.toModel(p1);
            return ResponseEntity.ok(paymentsRepModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentsRepModel adicionar(@Valid @RequestBody PaymentsInput paymentsInput) {

        Payments payments = paymentsMapper.toEntity(paymentsInput);
        payments.setPaymentDate(OffsetDateTime.parse(paymentsInput.getPaymentDate()));

        PaymentsRepModel paymentsRepModel = paymentsMapper.toModel(payments);

        customersRepository.findById(paymentsInput.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("There is no customer with that Id"));


        return paymentsRepModel;
    }

}