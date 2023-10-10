package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.OrdersMapper;
import com.classicmodels.api.model.OrdersRepModel;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Orders;
import com.classicmodels.domain.repository.OrdersRepository;
import com.classicmodels.domain.service.OrdersCatalogService;
import jakarta.persistence.criteria.Order;
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
                .orElseThrow(() -> new EntityNotFoundException("There is no Order Id " + id + " in the Table"));
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
            throw new EntityNotFoundException("There is no order date " + date + " in the Table");
        }
    }

    @GetMapping("/requireddate/{date}")
    public ResponseEntity<List<OrdersRepModel>> buscarPorOrderRequiredDate(@PathVariable String date) {

        List<Orders> orders = ordersCatalogService.buscarPorOrderRequiredDate(date);

        if (!orders.isEmpty()) {
            List<OrdersRepModel> ordersRepModels = orders.stream()
                    .map(ordersClass -> ordersMapper.toModel(ordersClass))
                    .toList();
            return ResponseEntity.ok(ordersRepModels);
        } else {
            throw new EntityNotFoundException("There is no order required date " + date + " in the Table");
        }
    }

    @GetMapping("/shippeddate/{date}")
    public ResponseEntity<List<OrdersRepModel>> buscarPorOrderShippedDate(@PathVariable String date) {

        List<Orders> orders = ordersCatalogService.buscarPorOrderShippedDate(date);

        if (!orders.isEmpty()) {
            List<OrdersRepModel> ordersRepModels = orders.stream()
                    .map(ordersClass -> ordersMapper.toModel(ordersClass))
                    .toList();
            return ResponseEntity.ok(ordersRepModels);
        } else {
            throw new EntityNotFoundException("There is no order shipped date " + date + " in the Table");
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrdersRepModel>> buscarPorOrderStatus(@PathVariable String status) {

        List<Orders> orders = ordersRepository.findByStatus(status);

        if (!orders.isEmpty()) {
            List<OrdersRepModel> ordersRepModels = orders.stream()
                    .map(ordersClass -> ordersMapper.toModel(ordersClass))
                    .toList();
            return ResponseEntity.ok(ordersRepModels);
        } else {
            throw new EntityNotFoundException("There is no order status " + status + " in the Table");
        }

    }

    @GetMapping("/customerid/{id}")
    public ResponseEntity<List<OrdersRepModel>> buscarPorOrderCustomerId(@PathVariable Long id) {

        List<Orders> orders = ordersRepository.findByCustomerId(id);

        if (!orders.isEmpty()) {
            List<OrdersRepModel> ordersRepModel = orders.stream()
                    .map(ordersClass -> ordersMapper.toModel(ordersClass))
                    .toList();
            return ResponseEntity.ok(ordersRepModel);
        } else {
            throw new EntityNotFoundException("there is no order customer_id " + id + " in the Table");
        }
    }

}
