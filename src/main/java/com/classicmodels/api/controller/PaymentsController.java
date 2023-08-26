package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.PaymentsMapper;
import com.classicmodels.api.model.OfficesRepModel;
import com.classicmodels.api.model.PaymentsRepModel;
import com.classicmodels.domain.model.Payments;
import com.classicmodels.domain.repository.PaymentsRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private PaymentsRepository paymentsRepository;
    private PaymentsMapper paymentsMapper;

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

}