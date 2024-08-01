package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.EmployeeMapper;
import com.classicmodels.api.model.EmployeeRepModel;
import com.classicmodels.api.model.input.EmployeeInput;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Employee;
import com.classicmodels.domain.model.Offices;
import com.classicmodels.domain.repository.EmployeesRepository;
import com.classicmodels.domain.repository.OfficesRepository;
import com.classicmodels.domain.service.EmployeesCatalogService;
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
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeesControllerTest {


    @Mock
    private EmployeesRepository employeesRepository;

    @Mock
    private OfficesRepository officesRepository;

    @Mock
    private EmployeesCatalogService employeesCatalogService;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeesController employeesController;

    private List<Employee> mockEmployeeList;
    private Employee mockEmployee;
    private EmployeeInput mockEmployeeInput;
    private Offices mockOffices;


    @BeforeEach
    public void setup() {

        mockEmployeeList = new ArrayList<>();
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setLastName("Leal");
        employee1.setFirstName("Marcelo");
        employee1.setExtension("123");
        employee1.setEmail("marcelo@hotmail.com");
        employee1.setJobTitle("President");
        employee1.setOfficeId(1L);
        mockEmployeeList.add(employee1);

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setLastName("LealRose");
        employee2.setFirstName("Rose");
        employee2.setExtension("123");
        employee2.setEmail("rose@hotmail.com");
        employee2.setJobTitle("Manager");
        employee2.setOfficeId(2L);
        mockEmployeeList.add(employee2);

        mockEmployee = new Employee();
        mockEmployee.setId(3L);
        mockEmployee.setLastName("Doe");
        mockEmployee.setFirstName("John");
        mockEmployee.setExtension("123");
        mockEmployee.setEmail("doe@hotmail.com");
        mockEmployee.setJobTitle("SalesRep");

        mockEmployeeInput = new EmployeeInput();
        mockEmployeeInput.setOfficeId(1L);
        mockEmployeeInput.setLastName("Leal");
        mockEmployeeInput.setFirstName("Marcelo");
        mockEmployeeInput.setExtension("123");
        mockEmployeeInput.setEmail("marcelo@hotmail.com");
        mockEmployeeInput.setJobTitle("President");
        mockEmployeeInput.setReportsTo(1L);

        mockOffices = new Offices();
        mockOffices.setId(1L);
        mockOffices.setCity("Mauá");
        mockOffices.setPhone("123");
        mockOffices.setAddressLine1("Nossa Rua");
        mockOffices.setAddressLine2("xxx");
        mockOffices.setState("SP");
        mockOffices.setCountry("Brazil");
        mockOffices.setPostalCode("123");
        mockOffices.setTerritory("xxx");

    }

    @Test
    public void testListarEmployees() {

        when(employeesRepository.findAll()).thenReturn(mockEmployeeList);
        when(employeeMapper.toCollectionModel(mockEmployeeList))
                .thenReturn(mockEmployeeList.stream()
                        .map(EmployeeRepModel::new)
                        .collect(Collectors.toList()));

        List<EmployeeRepModel> employeeRepModels = employeesController.listar();

        assertThat(employeeRepModels, is(not(empty())));
        assertThat(employeeRepModels, hasSize(mockEmployeeList.size()));

        EmployeeRepModel firstEmployeeRepModel = employeeRepModels.get(0);
        assertThat(firstEmployeeRepModel.getId(), is(1L));
        assertThat(firstEmployeeRepModel.getLastName(), is("Leal"));

        EmployeeRepModel secondEmployeeRepModel = employeeRepModels.get(1);
        assertThat(secondEmployeeRepModel.getId(), is(2L));
        assertThat(secondEmployeeRepModel.getLastName(), is("LealRose"));
    }

    @Test
    public void testBuscarPorIdFuncionarioExistente() {

        when(employeesRepository.findById(3L)).thenReturn(Optional.of(mockEmployee));
        when(employeeMapper.toModel(mockEmployee)).thenReturn(new EmployeeRepModel(mockEmployee));

        ResponseEntity<EmployeeRepModel> response = employeesController.buscarPorId(3L);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        EmployeeRepModel employeeRepModel = response.getBody();
        assertThat(employeeRepModel, is(notNullValue()));
        assertThat(employeeRepModel.getId(), is(3L));
        assertThat(employeeRepModel.getLastName(), is("Doe"));
        assertThat(employeeRepModel.getEmail(), is("doe@hotmail.com"));
    }

    @Test
    public void testBuscarPorIdFuncionarioNaoExistente() {

        when(employeesRepository.findById(4L)).thenReturn(Optional.empty());

        ResponseEntity<EmployeeRepModel> response = employeesController.buscarPorId(4L);

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(response.getBody(), is(nullValue()));
    }

    @Test
    public void testBuscarPorEmailExistente() {

        when(employeesRepository.findByEmail("marcelo@hotmail.com")).thenReturn(Optional.of(mockEmployeeList.get(0)));
        when(employeeMapper.toModel(mockEmployeeList.get(0))).thenReturn(new EmployeeRepModel(mockEmployeeList.get(0)));

        ResponseEntity<EmployeeRepModel> response = employeesController.buscarPorEmail("marcelo@hotmail.com");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        EmployeeRepModel employeeRepModel = response.getBody();
        assertThat(employeeRepModel, is(notNullValue()));
        assertThat(employeeRepModel.getEmail(), is("marcelo@hotmail.com"));
        assertThat(employeeRepModel.getLastName(), is("Leal"));
        assertThat(employeeRepModel.getId(), is(1L));
    }

    @Test
    public void testBuscarPorEmailNaoExistente() {

        when(employeesRepository.findByEmail("marcelo123@hotmail.com")).thenReturn(Optional.empty());

        ResponseEntity<EmployeeRepModel> response = employeesController.buscarPorEmail("marcelo123@hotmail.com");

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(response.getBody(), is(nullValue()));
    }

    @Test
    public void testAdicionarEmailExistente() {

        mockEmployeeInput.setReportsTo(1L);
        EmployeeRepModel response = new EmployeeRepModel();

        when(employeeMapper.toEntity(mockEmployeeInput)).thenReturn(mockEmployeeList.get(0));
        when(employeesRepository.findByEmail("marcelo@hotmail.com")).thenReturn(Optional.of(mockEmployeeList.get(0)));
        when(employeesRepository.findById(1L)).thenReturn(Optional.of(mockEmployeeList.get(0)));
        when(officesRepository.findById(1L)).thenReturn(Optional.ofNullable(mockOffices));
        when(employeesController.adicionar(mockEmployeeInput)).thenReturn(response);

        response = employeesController.adicionar(mockEmployeeInput);

        assertTrue(employeesRepository.findByEmail("marcelo@hotmail.com").isPresent());
    }

    @Test
    public void testAdicionarEmailNaoExistente() {

        mockEmployeeInput.setReportsTo(1L);
        EmployeeRepModel response = new EmployeeRepModel();

        when(employeeMapper.toEntity(mockEmployeeInput)).thenReturn(mockEmployeeList.get(0));
        when(employeesRepository.findByEmail("marcelo@hotmail.com")).thenReturn(Optional.of(mockEmployeeList.get(0)));
        when(employeesRepository.findById(1L)).thenReturn(Optional.of(mockEmployeeList.get(0)));
        when(officesRepository.findById(1L)).thenReturn(Optional.ofNullable(mockOffices));
        when(employeesController.adicionar(mockEmployeeInput)).thenReturn(response);

        response = employeesController.adicionar(mockEmployeeInput);

        assertFalse(employeesRepository.findByEmail("marcelo123@hotmail.com").isPresent());
    }

    @Test
    public void testEntityNotFoundException() {

        mockEmployeeInput.setReportsTo(1L);

        when(employeeMapper.toEntity(mockEmployeeInput)).thenReturn(mockEmployeeList.get(0));
        when(employeesRepository.findByEmail("marcelo@hotmail.com")).thenReturn(Optional.of(mockEmployeeList.get(0)));

        lenient().when(officesRepository.findById(2L)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> {
           employeesController.adicionar(mockEmployeeInput);
        });
    }

    @Test
    public void testUpdateEmployeeSuccess() {
        when(employeesRepository.findById(anyLong())).thenReturn(Optional.of(mockEmployee));
        when(employeeMapper.toEntity(any())).thenReturn(mockEmployee);
        when(officesRepository.findById(anyLong())).thenReturn(Optional.of(new Offices()));

        ResponseEntity<EmployeeRepModel> response = employeesController.atualizar(1L, mockEmployeeInput);

        verify(employeesCatalogService, times(1)).salvar(any());
        verify(employeeMapper, times(1)).toModel(any());
    }

    @Test
    public void testDeleteEmployeeSuccess() {

        when(employeesRepository.existsById(anyLong())).thenReturn(true);

        ResponseEntity<Void> response = employeesController.excluir(1L);

        verify(employeesCatalogService, times(1)).excluir(anyLong());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteEmployeeNotFound() {

        when(employeesRepository.existsById(anyLong())).thenReturn(false);

        ResponseEntity<Void> response = employeesController.excluir(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}