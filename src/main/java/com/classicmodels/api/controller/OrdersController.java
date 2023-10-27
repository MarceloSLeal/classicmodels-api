package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.OrdersMapper;
import com.classicmodels.api.model.OrdersRepModel;
import com.classicmodels.api.model.input.OrdersInput;
import com.classicmodels.api.model.input.OrdersInputUpdate;
import com.classicmodels.domain.exception.BusinessException;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Orders;
import com.classicmodels.domain.model.OrdersStatus;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.repository.OrdersRepository;
import com.classicmodels.domain.service.OrdersCatalogService;
import jakarta.persistence.criteria.Order;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrdersController {

    private OrdersRepository ordersRepository;
    private OrdersMapper ordersMapper;
    private OrdersCatalogService ordersCatalogService;
    private CustomersRepository customersRepository;

    @GetMapping
    public List<Orders> listar() {
        return ordersRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OrdersRepModel> buscarPorOrderId(@PathVariable Long id) {

        return ordersRepository.findById(id)
                .map(orders -> ResponseEntity.ok(ordersMapper.toModel(orders)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "This order Id %s doesn't exist".formatted(id)));

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This order date %s doesn't exist".formatted(date));
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
    public ResponseEntity<List<OrdersRepModel>> buscarPorOrderStatus(@PathVariable OrdersStatus status) {

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

    @PostMapping
    public ResponseEntity<OrdersRepModel> adicionar(@Valid @RequestBody OrdersInput ordersInput) {

        customersRepository.findById(ordersInput.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("There is no customer with the Id " + ordersInput.getCustomerId()));

        Orders orders = ordersMapper.toEntity(ordersInput);
        Orders savedOrders = ordersCatalogService.salvarPost(orders);

        return ResponseEntity.status(HttpStatus.CREATED).body(ordersMapper.toModel(savedOrders));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdersRepModel> atualizar(@PathVariable Long id, @Valid @RequestBody OrdersInputUpdate ordersInputUpdate) {

        try {
            Orders test = new Orders();
            test.setStatus(OrdersStatus.valueOf(ordersInputUpdate.getStatus()));
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Valid values for status: SHIPPED, RESOLVED, CANCELLED, ON_HOLD, DISPUTED, IN_PROCESS");
        }

        Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no order with the Id " + id));

        OrdersRepModel ordersRepModel = ordersMapper.toModel(ordersCatalogService.atualizar(orders, ordersInputUpdate));

        return ResponseEntity.ok(ordersRepModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        if (!ordersRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        ordersCatalogService.excluir(id);

        return ResponseEntity.noContent().build();
    }

}
