package com.classicmodels.api.controller;

import com.classicmodels.api.model.OrderDetailsRepModel;
import com.classicmodels.domain.model.OrderDetails;
import com.classicmodels.domain.repository.OrderDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/orderdetails")
public class OrderDetailsController {

    private OrderDetailsRepository orderDetailsRepository;

    @GetMapping
    public List<OrderDetails> listar() {
        return orderDetailsRepository.findAll();
    }

    @GetMapping("/orderid/{id}")
    public ResponseEntity<OrderDetailsRepModel> buscarPorOrderDetailId(@PathVariable Long id) {

        List<OrderDetails> orderDetails = orderDetailsRepository.findByOrderId(id);

        if (!orderDetails.isEmpty()) {
            OrderDetailsRepModel orderDetailsRepModel = new OrderDetailsRepModel(orderDetails);
            return ResponseEntity.ok(orderDetailsRepModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
