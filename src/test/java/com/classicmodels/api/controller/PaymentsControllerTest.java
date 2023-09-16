package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.PaymentsMapper;
import com.classicmodels.domain.model.Payments;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.repository.PaymentsRepository;
import com.classicmodels.domain.service.PaymentsCatalogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@WebMvcTest(PaymentsController.class)
public class PaymentsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PaymentsRepository paymentsRepository;
    @MockBean
    private PaymentsMapper paymentsMapper;
    @MockBean
    private PaymentsCatalogService paymentsCatalogService;
    @MockBean
    private CustomersRepository customersRepository;

    private List<Payments> mockPaymentsList;

    @BeforeEach
    public void setup(){
        Payments payments1 = new Payments();
        payments1.setPaymentDate(OffsetDateTime.now());
        payments1.setCheckNumber(UUID.fromString("34b97957-543c-11ee-9da2-0242ac120002"));
        payments1.setAmount(1001.0);
        Payments payments2 = new Payments();
        payments2.setPaymentDate(OffsetDateTime.now());
        payments2.setCheckNumber(UUID.fromString("6eea3201-543c-11ee-9da2-0242ac120002"));
        payments2.setAmount(1002.0);
        Payments payments3 = new Payments();
        payments3.setPaymentDate(OffsetDateTime.now());
        payments3.setCheckNumber(UUID.fromString("9765c73c-543c-11ee-9da2-0242ac120002"));
        payments3.setAmount(1003.0);

        mockPaymentsList = new ArrayList<>();

        mockPaymentsList.add(payments1);
        mockPaymentsList.add(payments2);
        mockPaymentsList.add(payments3);
    }

    @Test
    public void testListar() throws Exception {

        List<Payments> payments = new ArrayList<>();
        payments = mockPaymentsList;

        when(paymentsRepository.findAll()).thenReturn(payments);

        mockMvc.perform(get("http://localhost:8080/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount").value(1001.0))
                .andExpect(jsonPath("$[1].amount").value(1002.0))
                .andExpect(jsonPath("$[2].amount").value(1003.0))
                .andExpect(jsonPath("$[0].checkNumber").value("34b97957-543c-11ee-9da2-0242ac120002"));

    }

}
