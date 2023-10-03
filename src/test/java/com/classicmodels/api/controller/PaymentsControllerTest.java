package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.PaymentsMapper;
import com.classicmodels.api.model.PaymentsRepModel;
import com.classicmodels.domain.model.Payments;
import com.classicmodels.domain.repository.PaymentsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@WebMvcTest(PaymentsController.class)
public class PaymentsControllerTest {

    @Mock
    private PaymentsRepository paymentsRepository;
    @Mock
    private PaymentsMapper paymentsMapper;
    @InjectMocks
    private PaymentsController paymentsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListar() throws Exception {

        List<Payments> mockPaymentsList = new ArrayList<>();

        Payments payments1 = new Payments();
        payments1.setPaymentDate(OffsetDateTime.now());
        payments1.setAmount(1001.0);
        payments1.setCheckNumber(UUID.fromString("1a1c07e0-a04c-46ed-805f-290d573d5e40"));
        payments1.setCustomerId(1L);
        Payments payments2 = new Payments();
        payments2.setPaymentDate(OffsetDateTime.now());
        payments2.setAmount(1002.0);
        payments2.setCheckNumber(UUID.fromString("40688a64-5125-11ee-8eaa-0242ac120002"));
        payments2.setCustomerId(2L);

        mockPaymentsList.add(payments1);
        mockPaymentsList.add(payments2);

        when(paymentsRepository.findAll()).thenReturn(mockPaymentsList);

        List<Payments> result = paymentsController.listar();

        verify(paymentsRepository, times(1)).findAll();
        assertEquals(mockPaymentsList, result);
        verifyNoMoreInteractions(paymentsRepository);
    }

    @Test
    public void testBuscarPorCustomerIdComPagamentosEncontrados() {
        Long customerId = 1L;

        List<Payments> mockPaymentsList = new ArrayList<>();

        Payments payments1 = new Payments();
        payments1.setPaymentDate(OffsetDateTime.now());
        payments1.setAmount(1001.0);
        payments1.setCheckNumber(UUID.fromString("1a1c07e0-a04c-46ed-805f-290d573d5e40"));
        payments1.setCustomerId(1L);
        Payments payments2 = new Payments();
        payments2.setPaymentDate(OffsetDateTime.now());
        payments2.setAmount(1002.0);
        payments2.setCheckNumber(UUID.fromString("40688a64-5125-11ee-8eaa-0242ac120002"));
        payments2.setCustomerId(1L);

        mockPaymentsList.add(payments1);
        mockPaymentsList.add(payments2);

        when(paymentsRepository.findByCustomerId(customerId)).thenReturn(mockPaymentsList);
        when(paymentsMapper.toModel(any(Payments.class))).thenAnswer(invocation -> {
            Payments payment = invocation.getArgument(0);
            return new PaymentsRepModel();
        });

        ResponseEntity<List<PaymentsRepModel>> response = paymentsController.buscarPorCustomerId(customerId);

        assertEquals(200, response.getStatusCodeValue());

        List<PaymentsRepModel> responseBody = response.getBody();
        assert responseBody != null;
        assertEquals(mockPaymentsList.size(), responseBody.size());
        verify(paymentsRepository, times(1)).findByCustomerId(customerId);
        verify(paymentsMapper, times(mockPaymentsList.size())).toModel(any(Payments.class));
        verifyNoMoreInteractions(paymentsRepository, paymentsMapper);

    }

    @Test
    public void testBuscarPorCustomerIdSemPagamentosEncontrados() {
        Long customerId = 1L;

        when(paymentsRepository.findByCustomerId(customerId)).thenReturn(new ArrayList<>());

        ResponseEntity<List<PaymentsRepModel>> response = paymentsController.buscarPorCustomerId(customerId);

        assertEquals(404, response.getStatusCodeValue());
        verify(paymentsRepository, times(1)).findByCustomerId(customerId);
        verifyNoMoreInteractions(paymentsRepository);
        verifyNoInteractions(paymentsMapper);
    }


}



