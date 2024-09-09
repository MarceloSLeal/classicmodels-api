package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.OrderDetailsMapper;
import com.classicmodels.api.model.OrderDetailsRepModel;
import com.classicmodels.api.model.OrderDetailsRepModelOrderId;
import com.classicmodels.api.model.OrderDetailsRepModelProductId;
import com.classicmodels.api.model.input.OrderDetailsInput;
import com.classicmodels.domain.model.OrderDetails;
import com.classicmodels.domain.repository.OrderDetailsRepository;
import com.classicmodels.domain.service.OrderDetailsCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orderdetails")
public class OrderDetailsController {

    private OrderDetailsRepository orderDetailsRepository;
    private OrderDetailsMapper orderDetailsMapper;
    private OrderDetailsCatalogService orderDetailsCatalogService;

    @GetMapping
    public List<OrderDetails> listar() {
        return orderDetailsRepository.findAll();
    }

    @GetMapping("/orderid/{id}")
    public ResponseEntity<OrderDetailsRepModelOrderId> buscarPorOrderDetailId(@PathVariable Long id) {

        List<OrderDetails> orderDetails = orderDetailsRepository.findByOrderId(id);

        if (!orderDetails.isEmpty()) {
            OrderDetailsRepModelOrderId orderDetailsRepModelOrderId = new OrderDetailsRepModelOrderId(orderDetails);
            return ResponseEntity.ok(orderDetailsRepModelOrderId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/productid/{id}")
    public ResponseEntity<OrderDetailsRepModelProductId> buscarPorProductId(@PathVariable Long id) {

        List<OrderDetails> orderDetails = orderDetailsRepository.findByProductId(id);

        if (!orderDetails.isEmpty()) {
            OrderDetailsRepModelProductId orderDetailsRepModelProductId = new OrderDetailsRepModelProductId(orderDetails);
            return ResponseEntity.ok(orderDetailsRepModelProductId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<List<OrderDetailsRepModel>> adicionar(@Valid @RequestBody List<OrderDetailsInput> orderDetailsInput) {

        List<OrderDetails> orderDetails = orderDetailsMapper.toCollecionModel(orderDetailsInput);
        List<OrderDetails> savedOrderDetails = orderDetailsCatalogService.salvar(orderDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderDetailsMapper.toCollectionEntity(savedOrderDetails));
    }

}
