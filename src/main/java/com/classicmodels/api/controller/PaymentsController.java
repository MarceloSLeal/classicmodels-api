package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.PaymentsMapper;
import com.classicmodels.api.model.PaymentsRepModel;
import com.classicmodels.api.model.input.PaymentsInput;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Payments;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.repository.OrdersRepository;
import com.classicmodels.domain.repository.PaymentsRepository;
import com.classicmodels.domain.service.OrdersCatalogService;
import com.classicmodels.domain.service.PaymentsCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;
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
    private OrdersCatalogService ordersCatalogService;
    private OrdersRepository ordersRepository;

    @GetMapping
    public List<PaymentsRepModel> listar() {

        return paymentsMapper.toCollectionModel(paymentsRepository.findAll());
    }

    @GetMapping("/orderid/{id}")
    public ResponseEntity<List<PaymentsRepModel>> buscarPorOrderId(@PathVariable Long id) {

        List<Payments> payments = paymentsRepository.findByOrderId(id);

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
    public ResponseEntity<PaymentsRepModel> adicionar(@Valid @RequestBody PaymentsInput paymentsInput) {

        LocalDateTime agora = LocalDateTime.now();

        ordersRepository.findById(paymentsInput.getOrderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no order with that Id"));

        Payments payments = paymentsMapper.toEntity(paymentsInput);
        Payments savedPayment = paymentsCatalogService.salvar(payments);
        PaymentsRepModel paymentsRepModel = paymentsMapper.toModel(savedPayment);

        ordersCatalogService.updateOrderStatus(paymentsInput.getOrderId());

        return ResponseEntity.status(HttpStatus.CREATED).body(paymentsRepModel);
    }

    @PutMapping("/{checkNumber}")
    public ResponseEntity<PaymentsRepModel> atualizar(@PathVariable UUID checkNumber, @Valid @RequestBody PaymentsInput paymentsInput) {

//        customersRepository.findById(paymentsInput.getCustomerId())
//                .orElseThrow(() -> new EntityNotFoundException("There is no customer with that Id"));

        ordersRepository.findById(paymentsInput.getOrderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no order with that Id"));

        Payments payments = paymentsRepository.findByCheckNumber(checkNumber)
                .orElseThrow(() -> new EntityNotFoundException("There is no payment with that ckeckNumber"));

        payments.setPaymentDate(paymentsInput.getPaymentDate());

        return ResponseEntity.ok(paymentsMapper
                .toModel(paymentsCatalogService.salvar(payments)));
    }

    @DeleteMapping("/{checkNumber}")
    public ResponseEntity<Void> excluir(@PathVariable UUID checkNumber) {

        if (!paymentsRepository.existsById(checkNumber)) {
            return ResponseEntity.notFound().build();
        }

        paymentsCatalogService.excluir(checkNumber);

        return ResponseEntity.noContent().build();
    }

}