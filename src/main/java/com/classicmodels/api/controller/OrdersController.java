package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.OrdersMapper;
import com.classicmodels.api.mapper.OrdersUpdateMapper;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrdersController {

    private OrdersRepository ordersRepository;
    private OrdersMapper ordersMapper;
    private OrdersUpdateMapper ordersUpdateMapper;
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This order required date %s doens't exist".formatted(date));
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This order shipped date %s doesn't exist".formatted(date));
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ("This order status %s was not set in any registry," +
                            " Accepted values for status, SHIPPED, RESOLVED, CANCELLED, ON_HOLD, DISPUTED, DISPUTED").formatted(status));
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This order shipped date %s doesn't exist".formatted(id));
        }
    }

    @PostMapping
    public ResponseEntity<OrdersRepModel> adicionar(@Valid @RequestBody OrdersInput ordersInput) {

        System.out.println(ordersInput.getRequiredDate());

        customersRepository.findById(ordersInput.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "This customer Id %s doesn't exist".formatted(ordersInput.getCustomerId())));

        Orders orders = ordersMapper.toEntity(ordersInput);
        Orders savedOrders = ordersCatalogService.salvarPost(orders);

        return ResponseEntity.status(HttpStatus.CREATED).body(ordersMapper.toModel(savedOrders));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdersRepModel> atualizar(@PathVariable Long id,
                                                    @Valid @RequestBody OrdersInputUpdate ordersInputUpdate,
                                                    BindingResult result) {

        System.out.println(ordersInputUpdate.getShippedDate());

        try {
            Orders teste = new Orders();
            teste.setStatus(OrdersStatus.valueOf(ordersInputUpdate.getStatus()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Accepted values for status, SHIPPED, RESOLVED, CANCELLED, ON_HOLD, DISPUTED, DISPUTED");
        }

        Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "This Id %s doesn't exist".formatted(id)));

        OrdersRepModel ordersRepModel = ordersUpdateMapper.toModel(ordersCatalogService.atualizar(orders, ordersInputUpdate));

        return ResponseEntity.ok(ordersRepModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        if (!ordersRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This Id %s doesn't exist".formatted(id));
        }

        ordersCatalogService.excluir(id);

        return ResponseEntity.noContent().build();
    }

}
