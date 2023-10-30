package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.OrdersMapper;
import com.classicmodels.api.model.OrdersRepModel;
import com.classicmodels.api.model.input.OrdersInput;
import com.classicmodels.api.model.input.OrdersInputUpdate;
import com.classicmodels.domain.model.Customer;
import com.classicmodels.domain.model.Orders;
import com.classicmodels.domain.model.OrdersStatus;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.repository.OrdersRepository;
import com.classicmodels.domain.service.OrdersCatalogService;
import jakarta.validation.constraints.Null;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
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
    @Mock
    CustomersRepository customersRepository;

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

    @Test
    public void testBuscarPorOrderRequiredDateEncontrados() {
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

        when(ordersCatalogService.buscarPorOrderRequiredDate(orderDate)).thenReturn(mockOrdersList);
        when(ordersMapper.toModel(any(Orders.class))).thenAnswer(invocation -> new OrdersRepModel());

        ResponseEntity<List<OrdersRepModel>> response = ordersController.buscarPorOrderRequiredDate(orderDate);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        List<OrdersRepModel> responseBody = response.getBody();
        assert responseBody != null;
        verify(ordersCatalogService, times(1)).buscarPorOrderRequiredDate(orderDate);
        verify(ordersMapper, times(mockOrdersList.size())).toModel(any(Orders.class));
        verifyNoMoreInteractions(ordersRepository, ordersMapper);
    }

    @Test
    public void testBuscarPorOrderRequiredDateNaoEncontrado() {

        String orderDate = "2023-10-14";

        when(ordersRepository.findByRequiredDate("2023-12-12 00:00:00", "2023-12-12 59:59:59")).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> ordersController.buscarPorOrderRequiredDate(orderDate));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void testBuscarPorOrderShippedDateEncontrados() {

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

        when(ordersCatalogService.buscarPorOrderShippedDate(orderDate)).thenReturn(mockOrdersList);
        when(ordersMapper.toModel(any(Orders.class))).thenAnswer(invocation -> new OrdersRepModel());

        ResponseEntity<List<OrdersRepModel>> response = ordersController.buscarPorOrderShippedDate(orderDate);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        List<OrdersRepModel> responseBody = response.getBody();
        assert responseBody != null;
        verify(ordersCatalogService, times(1)).buscarPorOrderShippedDate(orderDate);
        verify(ordersMapper, times(mockOrdersList.size())).toModel(any(Orders.class));
        verifyNoMoreInteractions(ordersRepository, ordersMapper);
    }

    @Test
    public void testBuscarPorOrderShippedDateNaoEncontrado() {

        String orderDate = "2023-10-14";

        when(ordersRepository.findByShippedDate("2023-12-12 00:00:00", "2023-12-12 59:59:59")).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> ordersController.buscarPorOrderShippedDate(orderDate));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void testBuscarPorOrderStatusEncontrados() {

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

        when(ordersRepository.findByStatus(String.valueOf(OrdersStatus.IN_PROCESS))).thenReturn(mockOrdersList);
        when(ordersMapper.toModel(any(Orders.class))).thenAnswer(invocation -> new OrdersRepModel());

        ResponseEntity<List<OrdersRepModel>> response = ordersController.buscarPorOrderStatus(String.valueOf(OrdersStatus.IN_PROCESS));

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        List<OrdersRepModel> responseBody = response.getBody();
        assert responseBody != null;
        verify(ordersRepository, times(1)).findByStatus(String.valueOf(OrdersStatus.IN_PROCESS));
        verify(ordersMapper, times(mockOrdersList.size())).toModel(any(Orders.class));
        verifyNoMoreInteractions(ordersRepository, ordersMapper);
    }

    @Test
    public void testBuscarPorOrderStatusNaoEncontrado() {

        when(ordersRepository.findByStatus("CANCELLED")).thenReturn(new ArrayList<>());

        String status = "CANCELLED";
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            ordersController.buscarPorOrderStatus(status);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("This order status CANCELLED was not set in any registry, Accepted values for status, SHIPPED, RESOLVED, CANCELLED, ON_HOLD, DISPUTED, DISPUTED", exception.getReason());
    }

    @Test
    public void testBuscarPorOrderCustomerIdEncontrados() {

        Long customerId = 103L;

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
        orders2.setCustomerId(103L);

        mockOrdersList.add(orders1);
        mockOrdersList.add(orders2);

        when(ordersRepository.findByCustomerId(customerId)).thenReturn(mockOrdersList);
        when(ordersMapper.toModel(any(Orders.class))).thenAnswer(invocation -> new OrdersRepModel());

        ResponseEntity<List<OrdersRepModel>> response = ordersController.buscarPorOrderCustomerId(customerId);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testBuscarPorOrderCustomerIdNaoEncontrado() {

        Long customerId = 100L;

        when(ordersRepository.findByCustomerId(customerId)).thenReturn(new ArrayList<>());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> ordersController.buscarPorOrderCustomerId(customerId));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void testAdicionarComSucessso() {

        OrdersInput ordersInput = new OrdersInput();
        ordersInput.setComments("Teste");
        ordersInput.setCustomerId(1L);
        ordersInput.setRequiredDate(OffsetDateTime.parse("2023-12-12T00:00:00-03:00"));

        Orders orders = new Orders();
        orders.setCustomerId(1L);
        orders.setId(2L);
        orders.setStatus(OrdersStatus.IN_PROCESS);
        orders.setDate(OffsetDateTime.now());
        orders.setRequiredDate(OffsetDateTime.parse("2023-12-12T00:00:00-03:00"));

        when(customersRepository.findById(1L)).thenReturn(Optional.of(new Customer()));
        when(ordersRepository.save(orders)).thenReturn(orders);
        when(ordersMapper.toEntity(ordersInput)).thenReturn(orders);
        when(ordersCatalogService.salvarPost(orders)).thenReturn(orders);

        ResponseEntity<OrdersRepModel> response = ordersController.adicionar(ordersInput);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(customersRepository, times(1)).findById(ordersInput.getCustomerId());
        verify(ordersCatalogService, times(1)).salvarPost(orders);
    }

    @Test
    public void testAdicionarSemSucesso() {

        OrdersInput ordersInput = new OrdersInput();
        ordersInput.setComments("Teste");
        ordersInput.setCustomerId(1L);
        ordersInput.setRequiredDate(OffsetDateTime.parse("2023-12-12T00:00:00-03:00"));

        when(customersRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> ordersController.adicionar(ordersInput));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void testAtualizarComSucesso() {

        Orders orders = new Orders();
        when(ordersRepository.findById(1L)).thenReturn(Optional.of(orders));

        OrdersInputUpdate ordersInputUpdate = new OrdersInputUpdate();
        ordersInputUpdate.setStatus("SHIPPED");
        ordersInputUpdate.setShippedDate(OffsetDateTime.parse("2023-12-12T00:00:00-03:00"));

        ResponseEntity<OrdersRepModel> responseEntity = ordersController.atualizar(1L, ordersInputUpdate);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testAtualizarComStatusInvalido() {

        OrdersInputUpdate ordersInputUpdate = new OrdersInputUpdate();
        ordersInputUpdate.setStatus("INVALID_STATUS");
        ordersInputUpdate.setShippedDate(OffsetDateTime.parse("2023-12-12T00:00:00-03:00"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            ordersController.atualizar(1L, ordersInputUpdate);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Accepted values for status, SHIPPED, RESOLVED, CANCELLED, ON_HOLD, DISPUTED, DISPUTED", exception.getReason());
    }

    @Test
    public void testAtualizarPedidoInexistente() {

        when(ordersRepository.findById(1L)).thenReturn(Optional.empty());

        OrdersInputUpdate ordersInputUpdate = new OrdersInputUpdate();
        ordersInputUpdate.setStatus("SHIPPED");
        ordersInputUpdate.setShippedDate(OffsetDateTime.parse("2023-12-12T00:00:00-03:00"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            ordersController.atualizar(1L, ordersInputUpdate);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("This Id 1 doesn't exist", exception.getReason());
    }

    @Test
    public void testExcluirPedidoComSucesso() {

        when(ordersRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> responseEntity = ordersController.excluir(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(ordersCatalogService, times(1)).excluir(1L);
    }

    @Test
    public void testExcluirPedidoInexistente() {

        when(ordersRepository.existsById(1L)).thenReturn(false);

//        ResponseEntity<Void> responseEntity = ordersController.excluir(1L);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> ordersController.excluir(1L));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        verify(ordersCatalogService, never()).excluir(anyLong());
    }

}
