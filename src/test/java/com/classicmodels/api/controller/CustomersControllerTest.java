package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.CustomerMapper;
import com.classicmodels.api.model.CustomerRepModel;
import com.classicmodels.api.model.input.CustomerInput;
import com.classicmodels.domain.exception.BusinessException;
import com.classicmodels.domain.model.Customer;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.service.CustomerCatalogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomersControllerTest {

    @InjectMocks
    private CustomersController customersController;

    @Mock
    private CustomersRepository customersRepository;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private CustomerCatalogService customerCatalogService;

    private Customer customer;
    private CustomerInput customerInput;
    private CustomerRepModel customerRepModel;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customerInput = new CustomerInput();
        customerRepModel = new CustomerRepModel();

        customer.setId(1L);customer.setEmail("marcelosleal@outlook.com");customer.setName("Marcelo");customer.setContactLastName("Leal");
        customer.setContactFirstName("Rose");customer.setPhone("1111");customer.setAddressLine1("Nossa rua, 42");customer.setAddressLine2("xxx");
        customer.setCity("Mauá");customer.setState("SP");customer.setPostalCode("1111");customer.setCountry("Brazil");customer.setEmployee(null);

        customerInput.setEmail("marcelosleal@outlook.com");customerInput.setName("oi");customerInput.setContactLastName("oi");
        customerInput.setContactFirstName("oi");customerInput.setPhone("111");customerInput.setAddressLine1("xxx");customerInput.setAddressLine2("xxx");
        customerInput.setCity("xxx");customerInput.setState("xxx");customerInput.setPostalCode("xxx");customerInput.setCountry("oi");
        customerInput.setCreditLimit(1000.0);customerInput.setEmployeeId(1);

        customerRepModel.setId(customer.getId());customerRepModel.setEmail(customer.getEmail());customerRepModel.setName(customer.getName());
        customerRepModel.setContactLastName(customer.getContactLastName());customerRepModel.setContactFirstName(customer.getContactFirstName());
        customerRepModel.setPhone(customer.getPhone());customerRepModel.setAddressLine1(customer.getAddressLine1());customerRepModel.setAddressLine2(customer.getAddressLine2());
        customerRepModel.setCity(customer.getCity());customerRepModel.setState(customer.getState());customerRepModel.setPostalCode(customer.getPostalCode());
        customerRepModel.setCountry(customer.getCountry());customerRepModel.setCreditLimit(customer.getCreditLimit());customerRepModel.setEmployeeId(1L);
    }

    @Test
    public void buscarTodos() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        when(customersRepository.findAll()).thenReturn(customerList);

        List<CustomerRepModel> customerRepModelList = new ArrayList<>();
        customerRepModelList.add(customerRepModel);

        when(customerMapper.toCollectionModel(customerList)).thenReturn(customerRepModelList);

        List<CustomerRepModel> result = customersController.listar();

        assertEquals(customerRepModelList, result);
    }

    @Test
    public void buscarPorId() {
        when(customersRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(customerMapper.toModel(customer)).thenReturn(customerRepModel);

        ResponseEntity<CustomerRepModel> responseEntity = customersController.buscarPorId(customer.getId());

        verify(customersRepository).findById(customer.getId());
        verify(customerMapper).toModel(customer);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerRepModel, responseEntity.getBody());
    }

    @Test
    public void buscarPorIdNotFound() {
        when(customersRepository.findById(customer.getId())).thenReturn(Optional.empty());

        ResponseEntity<CustomerRepModel> responseEntity = customersController.buscarPorId(customer.getId());

        verify(customersRepository).findById(customer.getId());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void buscarPorEmail() {
        when(customersRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        when(customerMapper.toModel(customer)).thenReturn(customerRepModel);

        ResponseEntity<CustomerRepModel> responseEntity = customersController.buscarPorEmail(customer.getEmail());

        verify(customersRepository).findByEmail(customer.getEmail());
        verify(customerMapper).toModel(customer);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerRepModel, responseEntity.getBody());
    }

    @Test
    public void buscarPorEmailNotFound() {
        when(customersRepository.findByEmail(customer.getEmail())).thenReturn(Optional.empty());

        ResponseEntity<CustomerRepModel> responseEntity = customersController.buscarPorEmail(customer.getEmail());

        verify(customersRepository).findByEmail(customer.getEmail());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void adicionar() {
        when(customersRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(customerMapper.toEntity(any(CustomerInput.class))).thenReturn(customer);
        when(customerMapper.toModel(any(Customer.class))).thenReturn(customerRepModel);

        CustomerRepModel result = customersController.adicionar(customerInput);

        Assertions.assertEquals(customerRepModel, result);
        verify(customerMapper, times(1)).toEntity(customerInput);
        verify(customerMapper, times(1)).toModel(customer);
        verify(customersRepository, times(1)).findByEmail(anyString());
        verify(customerCatalogService, times(1)).salvar(customer);
    }

    @Test
    public void adicionarEmailRepetido() {
        Customer existingCustomer = new Customer();

        existingCustomer.setId(2L);
        existingCustomer.setEmail("marcelosleal@outlook.com");

        lenient().when(customersRepository.findByEmail(anyString())).thenReturn(Optional.of(existingCustomer));

        verify(customerMapper, times(0)).toEntity(customerInput);
        verify(customerMapper, times(0)).toModel(customer);
        verify(customerCatalogService, times(0)).salvar(any(Customer.class));
    }

    @Test
    public void testUpdateCustomerSuccess() {
        Long customerId = 1L;

        when(customersRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerMapper.toEntity(customerInput)).thenReturn(customer);
        lenient().when(customersRepository.findByEmail(customer.getEmail())).thenReturn(Optional.empty());
        when(customerMapper.toModel(customer)).thenReturn(customerRepModel);

        ResponseEntity<CustomerRepModel> response = customersController.atualizar(customerId, customerInput);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerRepModel, response.getBody());
        verify(customerCatalogService, times(1)).salvar(customer);
    }

    @Test
    public void testUpdateNonExistingCustomer() {
        Long nonExistingCustomerId = 999L;

        when(customersRepository.findById(nonExistingCustomerId)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> customersController.atualizar(nonExistingCustomerId, customerInput));
        verify(customerCatalogService, never()).salvar(any());
    }

    @Test
    public void testExcluirExistingCustomer() {
        Long customerIdToDelete = 1L;

        when(customersRepository.existsById(customerIdToDelete)).thenReturn(true);

        ResponseEntity<Void> response = customersController.excluir(customerIdToDelete);

        verify(customersRepository, times(1)).existsById(customerIdToDelete);

        verify(customerCatalogService, times(1)).excluir(customerIdToDelete);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testExcluirNonExistingCustomer() {
        Long customerIdToDelete = 2L;

        when(customersRepository.existsById(customerIdToDelete)).thenReturn(false);

        ResponseEntity<Void> response = customersController.excluir(customerIdToDelete);

        verify(customersRepository, times(1)).existsById(customerIdToDelete);

        verify(customerCatalogService, times(0)).excluir(customerIdToDelete);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}