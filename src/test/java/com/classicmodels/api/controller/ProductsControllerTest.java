package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.ProductsMapper;
import com.classicmodels.api.model.ProductsRepModel;
import com.classicmodels.api.model.input.ProductsInput;
import com.classicmodels.domain.model.Products;
import com.classicmodels.domain.repository.ProductsRepository;
import com.classicmodels.domain.service.ProductsCatalogService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProductsControllerTest {

    @Mock
    private ProductsRepository productsRepository;
    @Mock
    private ProductsMapper productsMapper;
    @Mock
    private ProductsCatalogService productsCatalogService;

    @InjectMocks
    private ProductsController productsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListar() {

        Products products1 = new Products();
        products1.setId(1L);
        products1.setName("Produto1");
        products1.setProductLine("Motorcycles");
        products1.setScale("1:10");
        products1.setVendor("Min Lin Diecast");
        products1.setDescription("Descrição prod1");
        products1.setQuantityInStock(10);
        products1.setBuyPrice(50.0);
        products1.setMsrp(90.0);
        Products products2 = new Products();
        products2.setId(2L);
        products2.setName("Produto1");
        products2.setProductLine("Motorcycles");
        products2.setScale("1:10");
        products2.setVendor("Min Lin Diecast");
        products2.setDescription("Descrição prod1");
        products2.setQuantityInStock(10);
        products2.setBuyPrice(50.0);
        products2.setMsrp(90.0);

        List<Products> mockProductsList = Arrays.asList(products1, products2);

        when(productsRepository.findAll()).thenReturn(mockProductsList);

        List<Products> result = productsController.listar();

        verify(productsRepository, times(1)).findAll();
        assertEquals(mockProductsList, result);
        verifyNoMoreInteractions(productsRepository);
    }

    @Test
    public void testBuscarPorProductIdEncontrado() {
        Long id = 1L;

        Products products1 = new Products();
        products1.setId(1L);
        products1.setName("Produto1");
        products1.setProductLine("Motorcycles");
        products1.setScale("1:10");
        products1.setVendor("Min Lin Diecast");
        products1.setDescription("Descrição prod1");
        products1.setQuantityInStock(10);
        products1.setBuyPrice(50.0);
        products1.setMsrp(90.0);

        when(productsRepository.findById(id)).thenReturn(Optional.of(products1));
        when(productsMapper.toModel(any(Products.class))).thenAnswer(invocation -> new ProductsRepModel());

        ResponseEntity<ProductsRepModel> response = productsController.buscarPorProductId(id);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        ProductsRepModel responseBody = response.getBody();
        assert  responseBody != null;
        verify(productsRepository, times(1)).findById(id);
        verify(productsMapper, times(1)).toModel(any(Products.class));
        verifyNoMoreInteractions(productsRepository, productsMapper);
    }

    @Test
    public void testBuscarPorProductIdNaoEncontrado() {
        Long id = 1L;

        when(productsRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> productsController.buscarPorProductId(id));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void testBuscarPorProductName() {
        String nome = "Produto1";

        Products products1 = new Products();
        products1.setId(1L);
        products1.setName("Produto1");
        products1.setProductLine("Motorcycles");
        products1.setScale("1:10");
        products1.setVendor("Min Lin Diecast");
        products1.setDescription("Descrição prod1");
        products1.setQuantityInStock(10);
        products1.setBuyPrice(50.0);
        products1.setMsrp(90.0);

        when(productsRepository.findByName(nome)).thenReturn(Optional.of(products1));
        when(productsMapper.toModel(any(Products.class))).thenAnswer(invocation -> new ProductsRepModel());

        ResponseEntity<ProductsRepModel> response = productsController.buscarPorProducName(nome);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        ProductsRepModel responseBody = response.getBody();
        assert responseBody != null;
        verify(productsRepository, times(1)).findByName(nome);
        verify(productsMapper, times(1)).toModel(any(Products.class));
        verifyNoMoreInteractions(productsRepository, productsMapper);
    }

    @Test
    public void testBuscarPorProductNameNaoEncontrado() {
        String name = "Oi";

        when(productsRepository.findByName(name)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> productsController.buscarPorProducName(name));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void testBuscarPorProductLine() {
        String productLine = "Motorcycles";

        Products products1 = new Products();
        products1.setId(1L);
        products1.setName("Produto1");
        products1.setProductLine("Motorcycles");
        products1.setScale("1:10");
        products1.setVendor("Min Lin Diecast");
        products1.setDescription("Descrição prod1");
        products1.setQuantityInStock(10);
        products1.setBuyPrice(50.0);
        products1.setMsrp(90.0);
        Products products2 = new Products();
        products2.setId(2L);
        products2.setName("Produto1");
        products2.setProductLine("Motorcycles");
        products2.setScale("1:10");
        products2.setVendor("Min Lin Diecast");
        products2.setDescription("Descrição prod1");
        products2.setQuantityInStock(10);
        products2.setBuyPrice(50.0);
        products2.setMsrp(90.0);

        List<Products> mockProductList = Arrays.asList(products1, products2);

        when(productsRepository.findByProductLine(productLine)).thenReturn(mockProductList);
        when(productsMapper.toModel(any(Products.class))).thenAnswer(invocation -> new ProductsRepModel());

        ResponseEntity<List<ProductsRepModel>> response = productsController.buscarPorProductLine(productLine);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        List<ProductsRepModel> responseBody = response.getBody();
        assert responseBody != null;
        verify(productsRepository, times(1)).findByProductLine(productLine);
        verify(productsMapper, times(2)).toModel(any(Products.class));
        verifyNoMoreInteractions(productsRepository, productsMapper);
    }

    @Test
    public void testBuscarPorProductLineNaoEncontrado() {
        String prouductLine = "Oi";

        when(productsRepository.findByProductLine(prouductLine)).thenReturn(Collections.emptyList());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> productsController.buscarPorProductLine(prouductLine));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void testBuscarPorScale() {
        String scale = "1:10";

        Products products1 = new Products();
        products1.setId(1L);
        products1.setName("Produto1");
        products1.setProductLine("Motorcycles");
        products1.setScale("1:10");
        products1.setVendor("Min Lin Diecast");
        products1.setDescription("Descrição prod1");
        products1.setQuantityInStock(10);
        products1.setBuyPrice(50.0);
        products1.setMsrp(90.0);
        Products products2 = new Products();
        products2.setId(2L);
        products2.setName("Produto1");
        products2.setProductLine("Motorcycles");
        products2.setScale("1:10");
        products2.setVendor("Min Lin Diecast");
        products2.setDescription("Descrição prod1");
        products2.setQuantityInStock(10);
        products2.setBuyPrice(50.0);
        products2.setMsrp(90.0);

        List<Products> mockProductList = Arrays.asList(products1, products2);

        when(productsRepository.findByScale(scale)).thenReturn(mockProductList);
        when(productsMapper.toModel(any(Products.class))).thenAnswer(invocation -> new ProductsRepModel());

        ResponseEntity<List<ProductsRepModel>> response = productsController.buscarPorProductScale(scale);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        List<ProductsRepModel> responseBody = response.getBody();
        assert responseBody != null;
        verify(productsRepository, times(1)).findByScale(scale);
        verify(productsMapper, times(2)).toModel(any(Products.class));
        verifyNoMoreInteractions(productsRepository, productsMapper);
    }

    @Test
    public void testBuscarPorScaleNaoEncontrado() {
        String scale = "Oi";

        when(productsRepository.findByScale(scale)).thenReturn(Collections.emptyList());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> productsController.buscarPorProductScale(scale));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void testBuscarPorVendor() {
        String vendor = "Min Lin Diecast";

        Products products1 = new Products();
        products1.setId(1L);
        products1.setName("Produto1");
        products1.setProductLine("Motorcycles");
        products1.setScale("1:10");
        products1.setVendor("Min Lin Diecast");
        products1.setDescription("Descrição prod1");
        products1.setQuantityInStock(10);
        products1.setBuyPrice(50.0);
        products1.setMsrp(90.0);
        Products products2 = new Products();
        products2.setId(2L);
        products2.setName("Produto1");
        products2.setProductLine("Motorcycles");
        products2.setScale("1:10");
        products2.setVendor("Min Lin Diecast");
        products2.setDescription("Descrição prod1");
        products2.setQuantityInStock(10);
        products2.setBuyPrice(50.0);
        products2.setMsrp(90.0);

        List<Products> mockProductList = Arrays.asList(products1, products2);

        when(productsRepository.findByVendor(vendor)).thenReturn(mockProductList);
        when(productsMapper.toModel(any(Products.class))).thenAnswer(invocation -> new ProductsRepModel());

        ResponseEntity<List<ProductsRepModel>> response = productsController.buscarPorVendor(vendor);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        List<ProductsRepModel> responseBody = response.getBody();
        assert responseBody != null;
        verify(productsRepository, times(1)).findByVendor(vendor);
        verify(productsMapper, times(2)).toModel(any(Products.class));
        verifyNoMoreInteractions(productsRepository, productsMapper);
    }

    @Test
    public void testBuscarPorVendorNaoEncontrado() {
        String vendor = "Oi";

        when(productsRepository.findByVendor(vendor)).thenReturn(Collections.emptyList());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> productsController.buscarPorVendor(vendor));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void testAdicionarcomSucesso() {

        ProductsInput productsInput1 = new ProductsInput();
        productsInput1.setName("Prod1");
        productsInput1.setProductLine("Motorcycles");
        productsInput1.setScale("1:10");
        productsInput1.setVendor("Min Lin Diecast");
        productsInput1.setDescription("Teste Prod1");
        productsInput1.setQuantityInStock(100);
        productsInput1.setBuyPrice(20.0);
        productsInput1.setMsrp(38.0);

        Products products1 = new Products();
        products1.setId(1L);
        products1.setName("Prod1");
        products1.setProductLine("Motorcycles");
        products1.setScale("1:10");
        products1.setVendor("Min Lin Diecast");
        products1.setDescription("Teste Prod1");
        products1.setQuantityInStock(100);
        products1.setBuyPrice(20.0);
        products1.setMsrp(38.0);

        when(productsRepository.findByName("Prod2")).thenReturn(Optional.of(products1));
        when(productsMapper.toEntity(productsInput1)).thenReturn(products1);
        when(productsCatalogService.salvar(products1)).thenReturn(products1);

        ResponseEntity<ProductsRepModel> response = productsController.adicionar(productsInput1);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(productsRepository, times(1)).findByName("Prod1");
        verify(productsCatalogService, times(1)).salvar(products1);
    }

    @Test
    public void testAdicionarSemSucesso() {

        ProductsInput productsInput1 = new ProductsInput();
        productsInput1.setName("Prod1");
        productsInput1.setProductLine("Motorcycles");
        productsInput1.setScale("1:10");
        productsInput1.setVendor("Min Lin Diecast");
        productsInput1.setDescription("Teste Prod1");
        productsInput1.setQuantityInStock(100);
        productsInput1.setBuyPrice(20.0);
        productsInput1.setMsrp(38.0);

        Products products1 = new Products();
        products1.setId(1L);
        products1.setName("Prod1");
        products1.setProductLine("Motorcycles");
        products1.setScale("1:10");
        products1.setVendor("Min Lin Diecast");
        products1.setDescription("Teste Prod1");
        products1.setQuantityInStock(100);
        products1.setBuyPrice(20.0);
        products1.setMsrp(38.0);

        when(productsRepository.findByName("Prod1")).thenReturn(Optional.of(products1));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> productsController.adicionar(productsInput1));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void testAtualizarComSucesso() {

        Long id = 1L;

        ProductsInput productsInput1 = new ProductsInput();
        productsInput1.setName("Prod1");
        productsInput1.setProductLine("Motorcycles");
        productsInput1.setScale("1:10");
        productsInput1.setVendor("Min Lin Diecast");
        productsInput1.setDescription("Teste Prod1");
        productsInput1.setQuantityInStock(100);
        productsInput1.setBuyPrice(20.0);
        productsInput1.setMsrp(38.0);

        Products products1 = new Products();
        products1.setId(1L);
        products1.setName("Prod1");
        products1.setProductLine("Motorcycles");
        products1.setScale("1:10");
        products1.setVendor("Min Lin Diecast");
        products1.setDescription("Teste Prod1");
        products1.setQuantityInStock(100);
        products1.setBuyPrice(20.0);
        products1.setMsrp(38.0);

        when(productsRepository.findById(products1.getId())).thenReturn(Optional.of(new Products()));
        when(productsMapper.toEntity(productsInput1)).thenReturn(new Products());
        when(productsCatalogService.salvar(any())).thenReturn(new Products());

        ResponseEntity<ProductsRepModel> response = productsController.atualizar(id, productsInput1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testExcluir() {
        Long id = 1L;

        when(productsRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = productsController.excluir(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(productsCatalogService, times(1)).excluir(id);
    }

    @Test
    public void testExcluiIdNaoEncontrado() {
        Long id = 1L;

        when(productsRepository.existsById(id)).thenReturn(false);

        ResponseEntity<Void> response = productsController.excluir(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verifyNoInteractions(productsCatalogService);
    }

}
