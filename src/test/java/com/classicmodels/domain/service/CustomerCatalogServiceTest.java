package com.classicmodels.domain.service;

import com.classicmodels.domain.model.Customers;
import com.classicmodels.domain.model.Employees;
import com.classicmodels.domain.repository.CustomersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerCatalogServiceTest {

    @InjectMocks
    CustomerCatalogService customerCatalogService;

    @Mock
    CustomersRepository customersRepository;

    Customers customers;
    Employees employees;

//    @BeforeEach
//    public void setUp() {
//        customers = new Customers(
//    }

}
