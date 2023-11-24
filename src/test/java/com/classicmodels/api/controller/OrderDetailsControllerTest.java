package com.classicmodels.api.controller;

import com.classicmodels.api.model.OrderDetailsRepModelOrderId;
import com.classicmodels.api.model.OrderDetailsRepModelProductId;
import com.classicmodels.domain.model.OrderDetails;
import com.classicmodels.domain.repository.OrderDetailsRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderDetailsControllerTest {

    @Mock
    private OrderDetailsRepository orderDetailsRepository;

    @InjectMocks
    private OrderDetailsController orderDetailsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListar() {

        List<OrderDetails> mockOrderDetailsList = new ArrayList<>();

        OrderDetails orderDetails1 = new OrderDetails();
        orderDetails1.setOrderId(1L);
        orderDetails1.setProductId(1L);
        orderDetails1.setQuantityOrdered(10);
        orderDetails1.setPriceEach(30.0);
        orderDetails1.setOrderLineNumber(1);
        OrderDetails orderDetails2 = new OrderDetails();
        orderDetails2.setOrderId(1L);
        orderDetails2.setProductId(2L);
        orderDetails2.setQuantityOrdered(15);
        orderDetails2.setPriceEach(20.0);
        orderDetails2.setOrderLineNumber(2);

        mockOrderDetailsList.add(orderDetails1);
        mockOrderDetailsList.add(orderDetails2);

        when(orderDetailsRepository.findAll()).thenReturn(mockOrderDetailsList);

        List<OrderDetails> result = orderDetailsController.listar();

        verify(orderDetailsRepository, times(1)).findAll();
        assertEquals(mockOrderDetailsList, result);
        verifyNoMoreInteractions(orderDetailsRepository);
    }

    @Test
    public void testBuscarPorOrderDetailId() {

        Long orderId = 1L;

        List<OrderDetails> mockOrderDetailsList = new ArrayList<>();

        OrderDetails orderDetails1 = new OrderDetails();
        orderDetails1.setOrderId(1L);
        orderDetails1.setProductId(1L);
        orderDetails1.setQuantityOrdered(10);
        orderDetails1.setPriceEach(30.0);
        orderDetails1.setOrderLineNumber(1);
        OrderDetails orderDetails2 = new OrderDetails();
        orderDetails2.setOrderId(1L);
        orderDetails2.setProductId(2L);
        orderDetails2.setQuantityOrdered(15);
        orderDetails2.setPriceEach(20.0);
        orderDetails2.setOrderLineNumber(2);

        mockOrderDetailsList.add(orderDetails1);
        mockOrderDetailsList.add(orderDetails2);

        when(orderDetailsRepository.findByOrderId(orderId)).thenReturn(mockOrderDetailsList);

        ResponseEntity<OrderDetailsRepModelOrderId> response = orderDetailsController.buscarPorOrderDetailId(orderId);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testBuscarPorOrderDetailIdNaoEncontrado() {

        Long orderId = 1L;

        when(orderDetailsRepository.findByOrderId(orderId)).thenReturn(new ArrayList<>());

        ResponseEntity<OrderDetailsRepModelOrderId> response = orderDetailsController.buscarPorOrderDetailId(orderId);

        assertThat(response.getStatusCode(), Matchers.is(HttpStatus.NOT_FOUND));
        verify(orderDetailsRepository, times(1)).findByOrderId(orderId);
        verifyNoMoreInteractions(orderDetailsRepository);
    }

    @Test
    public void testBuscarPorProductId() {

        Long productId = 1L;

        List<OrderDetails> mockOrderDetailsList = new ArrayList<>();

        OrderDetails orderDetails1 = new OrderDetails();
        orderDetails1.setOrderId(1L);
        orderDetails1.setProductId(1L);
        orderDetails1.setQuantityOrdered(10);
        orderDetails1.setPriceEach(30.0);
        orderDetails1.setOrderLineNumber(1);
        OrderDetails orderDetails2 = new OrderDetails();
        orderDetails2.setOrderId(1L);
        orderDetails2.setProductId(2L);
        orderDetails2.setQuantityOrdered(15);
        orderDetails2.setPriceEach(20.0);
        orderDetails2.setOrderLineNumber(2);

        mockOrderDetailsList.add(orderDetails1);
        mockOrderDetailsList.add(orderDetails2);

        when(orderDetailsRepository.findByProductId(productId)).thenReturn(mockOrderDetailsList);

        ResponseEntity<OrderDetailsRepModelProductId> response = orderDetailsController.buscarPorProductId(productId);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testBuscarPorProductIdNaoEncontrado() {

        Long productId = 1L;

        when(orderDetailsRepository.findByProductId(productId)).thenReturn(new ArrayList<>());

        ResponseEntity<OrderDetailsRepModelProductId> response = orderDetailsController.buscarPorProductId(productId);

        assertThat(response.getStatusCode(), Matchers.is(HttpStatus.NOT_FOUND));
        verify(orderDetailsRepository, times(1)).findByProductId(productId);
        verifyNoMoreInteractions(orderDetailsRepository);
    }

}
