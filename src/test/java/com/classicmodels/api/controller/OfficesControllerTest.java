package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.OfficesMapper;
import com.classicmodels.api.model.OfficesRepModel;
import com.classicmodels.api.model.input.OfficesInput;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Offices;
import com.classicmodels.domain.repository.OfficesRepository;
import com.classicmodels.domain.service.OfficesCatalogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfficesControllerTest {

    @Mock
    OfficesRepository officesRepository;

    @Mock
    OfficesMapper officesMapper;

    @Mock
    OfficesCatalogService officesCatalogService;

    @InjectMocks
    private OfficesController officesController;

    private List<Offices> mockOfficesList;
    private Offices mockOffices1;
    private OfficesInput mockOfficesInput;
    private OfficesRepModel mockOfficesRepModel;

    @BeforeEach
    public void setup() {

        mockOfficesList = new ArrayList<>();
        Offices offices1 = new Offices();
        Offices offices2 = new Offices();

        offices1.setId(1L); offices2.setId(2L);
        offices1.setCity("Mauá"); offices2.setCity("San Francisco");
        offices1.setPhone("xxx"); offices2.setPhone("650");
        offices1.setAddressLine1("Nossa rua"); offices2.setAddressLine1("Mark Street");
        offices1.setAddressLine2("xxx"); offices2.setAddressLine2("Suite 300");
        offices1.setState("SP"); offices2.setState("CA");
        offices1.setCountry("Brazil"); offices2.setCountry("USA");
        offices1.setPostalCode("123"); offices2.setPostalCode("94080");
        offices1.setTerritory("Sudeste"); offices2.setTerritory("NA");

        mockOfficesList.add(offices1);
        mockOfficesList.add(offices2);

        mockOffices1 = new Offices();
        mockOffices1.setId(3L);
        mockOffices1.setCity("Santo André");
        mockOffices1.setPhone("345");
        mockOffices1.setAddressLine1("Rua rua");
        mockOffices1.setAddressLine2("Rua 2");
        mockOffices1.setState("SP");
        mockOffices1.setCountry("Brazil");
        mockOffices1.setPostalCode("098");
        mockOffices1.setTerritory("Sudeste");

        mockOfficesInput = new OfficesInput();
        mockOfficesInput.setCity("Santo André");
        mockOfficesInput.setPhone("345");
        mockOfficesInput.setAddressLine1("Rua rua");
        mockOfficesInput.setAddressLine2("Rua 2");
        mockOfficesInput.setState("SP");
        mockOfficesInput.setCountry("Brazil");
        mockOfficesInput.setPostalCode("098");
        mockOfficesInput.setTerritory("Sudeste");

        mockOfficesRepModel = new OfficesRepModel();
        mockOfficesRepModel.setEmployeeId(null);
        mockOfficesRepModel.setId(3L);
        mockOfficesRepModel.setCity("Santo André");
        mockOfficesRepModel.setPhone("345");
        mockOfficesRepModel.setAddressLine1("Rua rua");
        mockOfficesRepModel.setAddressLine2("Rua 2");
        mockOfficesRepModel.setState("SP");
        mockOfficesRepModel.setCountry("Brazil");
        mockOfficesRepModel.setPostalCode("098");
        mockOfficesRepModel.setTerritory("Sudeste");

    }

    @Test
    public void testListarOffices() {

        when(officesRepository.findAll()).thenReturn(mockOfficesList);
        when(officesMapper.toCollectionModel(mockOfficesList))
                .thenReturn(mockOfficesList.stream()
                        .map(OfficesRepModel::new)
                        .collect(Collectors.toList()));

        List<OfficesRepModel> officesRepModel = officesController.listar();

        assertThat(officesRepModel, is(not(empty())));
        assertThat(officesRepModel, hasSize(mockOfficesList.size()));
    }

    @Test
    public void testBuscarPorIdOfficesExistente() {

        when(officesRepository.findById(3L)).thenReturn(Optional.of(mockOffices1));
        when(officesMapper.toModel(mockOffices1)).thenReturn(new OfficesRepModel(mockOffices1));

        ResponseEntity<OfficesRepModel> response = officesController.buscaPorId(3L);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        OfficesRepModel officesRepModel = response.getBody();
        assertThat(officesRepModel, is(notNullValue()));
        assertThat(officesRepModel.getId(), is(3L));
        assertThat(officesRepModel.getCity(), is("Santo André"));
    }

    @Test
    public void testBuscarPorIdOfficesNaoExistente() {

        when(officesRepository.findById(4L)).thenReturn(Optional.empty());

        ResponseEntity<OfficesRepModel> response = officesController.buscaPorId(4L);

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(response.getBody(), is(nullValue()));
    }


    @Test
    public void testAdicionarOfficesSucesso() {

        when(officesController.adicionar(mockOfficesInput)).thenReturn(mockOfficesRepModel);

        OfficesRepModel response = officesController.adicionar(mockOfficesInput);

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void testAtualizarOfficesSucesso() {

        mockOfficesInput.setCity("Mauá");

        when(officesRepository.findById(anyLong())).thenReturn(Optional.of(mockOffices1));
        when(officesMapper.toEntity(Mockito.any())).thenReturn(mockOffices1);

        ResponseEntity<OfficesRepModel> response = officesController.atualizar(3L, mockOfficesInput);

        verify(officesCatalogService, times(1)).salvar(ArgumentMatchers.any());
        verify(officesMapper, times(1)).toModel(ArgumentMatchers.any());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void testAtualizarOfficesIdNaoEncontrado() {

        when(officesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            officesController.atualizar(1L, mockOfficesInput);
        });
    }

    @Test
    public void testExcluirSucesso() {

        when(officesRepository.existsById(anyLong())).thenReturn(true);

        ResponseEntity<Void> response = officesController.excluir(1L);

        verify(officesCatalogService, times(1)).excluir(anyLong());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testExcluirNotFound() {

        when(officesRepository.existsById(anyLong())).thenReturn(false);

        ResponseEntity<Void> response = officesController.excluir(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


}
