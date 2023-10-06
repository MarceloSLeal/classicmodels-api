package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.PaymentsMapper;
import com.classicmodels.api.model.PaymentsRepModel;
import com.classicmodels.api.model.input.PaymentsInput;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Customer;
import com.classicmodels.domain.model.Payments;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.repository.PaymentsRepository;
import com.classicmodels.domain.service.PaymentsCatalogService;
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
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

//@WebMvcTest(PaymentsController.class)
public class PaymentsControllerTest {

    @Mock
    private PaymentsRepository paymentsRepository;
    @Mock
    private PaymentsMapper paymentsMapper;
    @Mock
    private CustomersRepository customersRepository;
    @Mock
    private PaymentsCatalogService paymentsCatalogService;
    @InjectMocks
    private PaymentsController paymentsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListar() {

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
        when(paymentsMapper.toModel(any(Payments.class))).thenAnswer(invocation -> new PaymentsRepModel());

        ResponseEntity<List<PaymentsRepModel>> response = paymentsController.buscarPorCustomerId(customerId);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

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

        assertThat(response.getStatusCode(), Matchers.is(HttpStatus.NOT_FOUND));
        verify(paymentsRepository, times(1)).findByCustomerId(customerId);
        verifyNoMoreInteractions(paymentsRepository);
        verifyNoInteractions(paymentsMapper);
    }

    @Test
    public void testBuscarPorCheckNumberEncontrado() {

        Payments payments1 = new Payments();
        payments1.setPaymentDate(OffsetDateTime.now());
        payments1.setAmount(1001.0);
        payments1.setCheckNumber(UUID.fromString("1a1c07e0-a04c-46ed-805f-290d573d5e40"));
        payments1.setCustomerId(1L);

        when(paymentsRepository.findByCheckNumber(payments1.getCheckNumber())).thenReturn(Optional.of(payments1));
        when(paymentsMapper.toModel(any(Payments.class))).thenAnswer(invocation -> new PaymentsRepModel());

        ResponseEntity<PaymentsRepModel> response = paymentsController.buscarPorCheckNumber(String.valueOf(payments1.getCheckNumber()));

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        PaymentsRepModel responseBody = response.getBody();
        assert responseBody != null;

        verify(paymentsRepository, times(1)).findByCheckNumber(payments1.getCheckNumber());
    }

    @Test
    public void testAdicionarComSucesso() {

        PaymentsInput paymentsInput = new PaymentsInput();
        paymentsInput.setCustomerId(1L);
        paymentsInput.setPaymentDate(OffsetDateTime.now());
        paymentsInput.setAmount(1000.1);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setEmail("meuemail@meuemail.com");
        customer.setName("Marcelo");
        customer.setContactLastName("Leal");
        customer.setContactFirstName("Rose");
        customer.setPhone("123");
        customer.setAddressLine1("Minha Rua");
        customer.setCity("Mauá");
        customer.setCountry("Brasil");
        customer.setCreditLimit(1000.00);

        Payments payments = new Payments();
        payments.setPaymentDate(OffsetDateTime.now());
        payments.setAmount(1001.0);
        payments.setCheckNumber(UUID.fromString("1a1c07e0-a04c-46ed-805f-290d573d5e40"));
        payments.setCustomerId(1L);

        PaymentsRepModel paymentsRepModel = new PaymentsRepModel();
        paymentsRepModel.setCheckNumber(payments.getCheckNumber());
        paymentsRepModel.setCustomerId(1L);
        paymentsRepModel.setAmount(1000.1);
        paymentsRepModel.setPaymentDate(OffsetDateTime.now());

        when(customersRepository.findById(paymentsInput.getCustomerId())).thenReturn(Optional.of(customer));
        when(paymentsMapper.toEntity(paymentsInput)).thenReturn(payments);
        when(paymentsCatalogService.salvar(payments)).thenReturn(payments);

        ResponseEntity<PaymentsRepModel> responseEntity = paymentsController.adicionar(paymentsInput);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(customersRepository, times(1)).findById(paymentsInput.getCustomerId());
        verify(paymentsMapper, times(1)).toEntity(paymentsInput);
        verify(paymentsCatalogService, times(1)).salvar(payments);
    }

    @Test
    public void testAdicionarComCustomerIdNaoEncontrado() {

        PaymentsInput paymentsInput = new PaymentsInput();
        paymentsInput.setCustomerId(1L);
        paymentsInput.setPaymentDate(OffsetDateTime.now());
        paymentsInput.setAmount(1000.1);

        when(customersRepository.findById(paymentsInput.getCustomerId())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> paymentsController.adicionar(paymentsInput));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("There is no customer with that Id", exception.getReason());
        verify(customersRepository, times(1)).findById(paymentsInput.getCustomerId());
        verifyNoInteractions(paymentsMapper);
        verifyNoInteractions(paymentsCatalogService);
    }

    @Test
    public void testAtualizarComSucesso() {

        UUID checkNumber = UUID.fromString("0db46a97-6af8-4470-a285-3e01e7053bab");
        PaymentsInput paymentsInput = new PaymentsInput();
        paymentsInput.setCustomerId(1L);
        paymentsInput.setPaymentDate(OffsetDateTime.now());
        paymentsInput.setAmount(1002.1);

        when(customersRepository.findById(paymentsInput.getCustomerId())).thenReturn(Optional.of(new Customer()));
        when(paymentsRepository.findByCheckNumber(checkNumber)).thenReturn(Optional.of(new Payments()));
        when(paymentsMapper.toEntity(paymentsInput)).thenReturn(new Payments());
        when(paymentsCatalogService.salvar(any())).thenReturn(new Payments());

        ResponseEntity<PaymentsRepModel> responseEntity = paymentsController.atualizar(checkNumber, paymentsInput);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testAtualizarComCustomerNaoEncontrado() {

        UUID checkNumber = UUID.randomUUID();
        PaymentsInput paymentsInput = new PaymentsInput();
        paymentsInput.setCustomerId(1L);
        paymentsInput.setPaymentDate(OffsetDateTime.now());
        paymentsInput.setAmount(1002.1);

        when(customersRepository.findById(paymentsInput.getCustomerId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> paymentsController.atualizar(checkNumber, paymentsInput));
    }

    @Test
    public void testAtualizarComPaymentNaoEncontrado() {

        UUID checkNumber = UUID.randomUUID();
        PaymentsInput paymentsInput = new PaymentsInput();
        paymentsInput.setCustomerId(1L);
        paymentsInput.setPaymentDate(OffsetDateTime.now());
        paymentsInput.setAmount(1002.1);

        when(customersRepository.findById(paymentsInput.getCustomerId())).thenReturn(Optional.of(new Customer()));
        when(paymentsRepository.findByCheckNumber(checkNumber)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> paymentsController.atualizar(checkNumber, paymentsInput));
    }

    @Test
    public void testExcluirComSucesso() {
        UUID checkNumber = UUID.randomUUID();

        when(paymentsRepository.existsById(checkNumber)).thenReturn(true);

        ResponseEntity<Void> responseEntity = paymentsController.excluir(checkNumber);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(paymentsCatalogService, times(1)).excluir(checkNumber);
    }

    @Test
    public void testExcluirComPaymentNaoEncontrado() {
        UUID checkNumber = UUID.randomUUID();

        when(paymentsRepository.existsById(checkNumber)).thenReturn(false);

        ResponseEntity<Void> responseEntity = paymentsController.excluir(checkNumber);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verifyNoInteractions(paymentsCatalogService);
    }

}