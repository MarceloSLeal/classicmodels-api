package com.classicmodels.api.controller;

import com.classicmodels.domain.model.Orders;
import com.classicmodels.domain.repository.OrdersRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrdersController {

    private OrdersRepository ordersRepository;

    @GetMapping
    public List<Orders> listar() {
        return ordersRepository.findAll();
    }

}
