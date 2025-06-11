package com.classicmodels.domain.service;

import com.classicmodels.domain.model.Customer;
import com.classicmodels.domain.model.Employee;
import com.classicmodels.domain.repository.CustomersRepository;
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

    Customer customer;
    Employee employee;

//    @BeforeEach
//    public void setUp() {
//        customers = new Customers(
//    }

}
