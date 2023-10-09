package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.OrdersMapper;
import com.classicmodels.api.model.OrdersRepModel;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Orders;
import com.classicmodels.domain.repository.OrdersRepository;
import com.classicmodels.domain.service.OrdersCatalogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrdersController {

    private OrdersRepository ordersRepository;
    private OrdersMapper ordersMapper;
    private OrdersCatalogService ordersCatalogService;

    @GetMapping
    public List<Orders> listar() {
        return ordersRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OrdersRepModel> buscarPorOrderId(@PathVariable Long id) {

        return ordersRepository.findById(id)
                .map(orders -> ResponseEntity.ok(ordersMapper.toModel(orders)))
                .orElseThrow(() -> new EntityNotFoundException("There is no Order with this Id " + id));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<OrdersRepModel>> buscarPorOrderDate(@PathVariable String date) {

        List<Orders> orders = ordersCatalogService.buscarPorOrderDate(date);

        if (!orders.isEmpty()) {
            List<OrdersRepModel> ordersRepModels = orders.stream()
                    .map(ordersClass -> ordersMapper.toModel(ordersClass))
                    .toList();
            return ResponseEntity.ok(ordersRepModels);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
