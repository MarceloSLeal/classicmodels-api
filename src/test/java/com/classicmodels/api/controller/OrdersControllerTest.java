package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.OrdersMapper;
import com.classicmodels.api.model.OrdersRepModel;
import com.classicmodels.domain.model.Orders;
import com.classicmodels.domain.model.OrdersStatus;
import com.classicmodels.domain.repository.OrdersRepository;
import com.classicmodels.domain.service.OrdersCatalogService;
import jakarta.validation.constraints.Null;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class OrdersControllerTest {

    @Mock
    OrdersRepository ordersRepository;
    @Mock
    OrdersMapper ordersMapper;
    @Mock
    OrdersCatalogService ordersCatalogService;


    @InjectMocks
    OrdersController ordersController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListar() {

        List<Orders> mockOrdersList = new ArrayList<>();

        Orders orders1 = new Orders();
        orders1.setId(1L);
        orders1.setDate(OffsetDateTime.parse("2023-10-14T00:00:00-03:00"));
        orders1.setRequiredDate(OffsetDateTime.parse("2023-12-12T00:00:00-03:00"));
        orders1.setShippedDate(null);
        orders1.setStatus(OrdersStatus.IN_PROCESS);
        orders1.setComments("Orders1");
        orders1.setCustomerId(103L);
        Orders orders2 = new Orders();
        orders2.setId(2L);
        orders2.setDate(OffsetDateTime.parse("2023-10-14T00:00:00-03:00"));
        orders2.setRequiredDate(OffsetDateTime.parse("2023-12-12T00:00:00-03:00"));
        orders2.setShippedDate(null);
        orders2.setStatus(OrdersStatus.IN_PROCESS);
        orders2.setComments("Orders2");
        orders2.setCustomerId(104L);

        mockOrdersList.add(orders1);
        mockOrdersList.add(orders2);

        when(ordersRepository.findAll()).thenReturn(mockOrdersList);

        List<Orders> result = ordersController.listar();

        verify(ordersRepository, times(1)).findAll();
        assertEquals(mockOrdersList, result);
        verifyNoMoreInteractions(ordersRepository);
    }

    @Test
    public void testBuscarPorOrderIdEncontrado() {

        Long orderId = 10123L;

        Orders orders1 = new Orders();
        orders1.setId(1L);
        orders1.setDate(OffsetDateTime.parse("2023-10-14T00:00:00-03:00"));
        orders1.setRequiredDate(OffsetDateTime.parse("2023-12-12T00:00:00-03:00"));
        orders1.setShippedDate(null);
        orders1.setStatus(OrdersStatus.IN_PROCESS);
        orders1.setComments("Orders1");
        orders1.setCustomerId(103L);

        when(ordersRepository.findById(orderId)).thenReturn(Optional.of(orders1));
        when(ordersMapper.toModel(any(Orders.class))).thenAnswer(invocation -> new OrdersRepModel());

        ResponseEntity<OrdersRepModel> response = ordersController.buscarPorOrderId(orderId);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        OrdersRepModel responseBody = response.getBody();
        assert responseBody != null;
        verify(ordersRepository, times(1)).findById(orderId);
        verify(ordersMapper, times(1)).toModel(any(Orders.class));
        verifyNoMoreInteractions(ordersRepository);
    }

    @Test
    public void testBuscarPorOrderIdNaoEncontrado() {

        Long orderId = 100L;

        when(ordersRepository.findById(orderId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> ordersController.buscarPorOrderId(orderId));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void testBuscarPorOrderDateEncontrados() {

        String orderDate = "2023-10-14";

        List<Orders> mockOrdersList = new ArrayList<>();

        Orders orders1 = new Orders();
        orders1.setId(1L);
        orders1.setDate(OffsetDateTime.parse("2023-10-14T00:00:00-03:00"));
        orders1.setRequiredDate(OffsetDateTime.parse("2023-12-12T00:00:00-03:00"));
        orders1.setShippedDate(null);
        orders1.setStatus(OrdersStatus.IN_PROCESS);
        orders1.setComments("Orders1");
        orders1.setCustomerId(103L);
        Orders orders2 = new Orders();
        orders2.setId(2L);
        orders2.setDate(OffsetDateTime.parse("2023-10-14T00:00:00-03:00"));
        orders2.setRequiredDate(OffsetDateTime.parse("2023-12-12T00:00:00-03:00"));
        orders2.setShippedDate(null);
        orders2.setStatus(OrdersStatus.IN_PROCESS);
        orders2.setComments("Orders2");
        orders2.setCustomerId(104L);

        mockOrdersList.add(orders1);
        mockOrdersList.add(orders2);

        when(ordersCatalogService.buscarPorOrderDate(orderDate)).thenReturn(mockOrdersList);
        when(ordersMapper.toModel(any(Orders.class))).thenAnswer(invocation -> new OrdersRepModel());

        ResponseEntity<List<OrdersRepModel>> response = ordersController.buscarPorOrderDate(orderDate);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        List<OrdersRepModel> responseBody = response.getBody();
        assert responseBody != null;
        verify(ordersCatalogService, times(1)).buscarPorOrderDate(orderDate);
        verify(ordersMapper, times(mockOrdersList.size())).toModel(any(Orders.class));
        verifyNoMoreInteractions(ordersRepository, ordersMapper);
    }

    @Test
    public void testBuscarPorOrderDateNaoEncontrados() {

        String orderDate = "2023-10-14";

        when(ordersRepository.findByDate("2023-12-12 00:00:00", "2023-12-12 59:59:59")).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> ordersController.buscarPorOrderDate(orderDate));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

}
