package com.classicmodels.api.controller;


import com.classicmodels.api.mapper.PaymentsMapper;
import com.classicmodels.domain.model.Payments;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.repository.PaymentsRepository;
import com.classicmodels.domain.service.PaymentsCatalogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@WebMvcTest(PaymentsController.class)
public class PaymentsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext wac;


    @MockBean
    private PaymentsRepository paymentsRepository;
    @MockBean
    private PaymentsController paymentsController;
    @MockBean
    private PaymentsMapper paymentsMapper;

    @MockBean
    private PaymentsCatalogService paymentsCatalogService;
    @MockBean
    private CustomersRepository customersRepository;

    private List<Payments> mockPaymentsList;

    @BeforeEach
    public void setup() {
        Payments payments1 = new Payments();
        payments1.setPaymentDate(OffsetDateTime.now());
        payments1.setCheckNumber(UUID.fromString("34b97957-543c-11ee-9da2-0242ac120002"));
        payments1.setAmount(1001.0);
        payments1.setCustomerId(1L);
        Payments payments2 = new Payments();
        payments2.setPaymentDate(OffsetDateTime.now());
        payments2.setCheckNumber(UUID.fromString("6eea3201-543c-11ee-9da2-0242ac120002"));
        payments2.setAmount(1002.0);
        payments2.setCustomerId(2L);
        Payments payments3 = new Payments();
        payments3.setPaymentDate(OffsetDateTime.now());
        payments3.setCheckNumber(UUID.fromString("9765c73c-543c-11ee-9da2-0242ac120002"));
        payments3.setAmount(1003.0);
        payments3.setCustomerId(3L);
        Payments payments4 = new Payments();
        payments4.setPaymentDate(OffsetDateTime.now());
        payments4.setCheckNumber(UUID.fromString("defe37cb-565e-11ee-8c61-0242ac120002"));
        payments4.setAmount(1004.0);
        payments4.setCustomerId(1L);

        mockPaymentsList = new ArrayList<>();
        mockPaymentsList.add(payments1);
        mockPaymentsList.add(payments2);
        mockPaymentsList.add(payments3);
        mockPaymentsList.add(payments4);

    }


}



