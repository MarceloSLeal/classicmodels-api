package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.EmployeeMapper;
import com.classicmodels.api.model.EmployeeRepModel;
import com.classicmodels.api.model.input.EmployeeInput;
import com.classicmodels.api.model.lists.EmployeeRepModelIdNameList;
import com.classicmodels.api.model.lists.interfaces.EmployeeIdNameProjection;
import com.classicmodels.domain.exception.BusinessException;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Employee;
import com.classicmodels.domain.repository.EmployeesRepository;
import com.classicmodels.domain.repository.OfficesRepository;
import com.classicmodels.domain.service.EmployeesCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private EmployeesRepository employeesRepository;
    private EmployeesCatalogService employeesCatalogService;
    private EmployeeMapper employeeMapper;
    private OfficesRepository officesRepository;

    @GetMapping
    public List<EmployeeRepModel> listar() {

        return employeeMapper.toCollectionModel(employeesRepository.findAll());
    }

    @GetMapping("/employeeslist")
    public ResponseEntity<List<EmployeeRepModelIdNameList>> buscarPorEmployeesIds() {

        List<EmployeeIdNameProjection> projections = employeesRepository.findIdsByJobTitleSalesRep();
        if (projections.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EmployeeRepModelIdNameList> employeeIds = projections.stream()
                .map(projection -> {
                    EmployeeRepModelIdNameList dto = new EmployeeRepModelIdNameList();
                    dto.setId(projection.getId());
                    dto.setLastName(projection.getLastName());
                    dto.setFirstName(projection.getFirstName());
                    dto.setJobTitle(projection.getJobTitle());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeIds);
    }

    @GetMapping("/idname")
    public ResponseEntity<List<EmployeeRepModelIdNameList>> buscarPorIdName() {

        List<EmployeeIdNameProjection> projections = employeesRepository.findIdName();
        if (projections.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EmployeeRepModelIdNameList> employeeIds = projections.stream()
                .map(projection -> {
                    EmployeeRepModelIdNameList dto = new EmployeeRepModelIdNameList();
                    dto.setId(projection.getId());
                    dto.setLastName(projection.getLastName());
                    dto.setFirstName(projection.getFirstName());
                    dto.setJobTitle(projection.getJobTitle());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeIds);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeRepModel> buscarPorId(@PathVariable Long id) {

        return employeesRepository.findById(id)
                .map(employee -> ResponseEntity.ok(employeeMapper.toModel(employee)))
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/findbyemail/{email}")
    public ResponseEntity<EmployeeRepModel> buscarPorEmail(@PathVariable String email) {

        return employeesRepository.findByEmail(email)
                .map(employee -> ResponseEntity.ok(employeeMapper.toModel(employee)))
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeRepModel adicionar(@Valid @RequestBody EmployeeInput employeeInput) {

        Employee newEmployee = employeeMapper.toEntity(employeeInput);

        EmployeeRepModel employeeRepModel = employeeMapper.toModel(newEmployee);

        boolean employeeEmail = employeesRepository.findByEmail(newEmployee.getEmail())
                .stream()
                .anyMatch(employeeExists -> !employeeExists.equals(newEmployee));

        officesRepository.findById(employeeInput.getOfficeId())
                .orElseThrow(() -> new EntityNotFoundException("There is no office with that Id"));

        if (employeeEmail) {
            throw  new BusinessException("There is already a employee registered with this email");
        }

        if (employeeInput.getReportsTo() != null) {
            employeesRepository.findById(employeeInput.getReportsTo())
                    .orElseThrow(() -> new EntityNotFoundException("There is no employee with that id " + employeeInput.getReportsTo()));
        }

        employeesCatalogService.salvar(newEmployee);
        return employeeRepModel;
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeRepModel> atualizar(@PathVariable Long id, @Valid @RequestBody EmployeeInput employeeInput) {

        Employee employee = employeesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not exists"));
        Employee employeeEdit = employeeMapper.toEntity(employeeInput);
        employeeEdit.setId(id);
        //employeeEdit.setOfficeId(employeeInput.getOfficeId());

        officesRepository.findById(employeeInput.getOfficeId())
                .orElseThrow(() -> new EntityNotFoundException("Office not exists"));

        boolean emailEmployeeIgualEmployeeEdit = employee.getEmail().equals(employeeEdit.getEmail());
        boolean email;

        if (employeeInput.getReportsTo() != null) {
            employeesRepository.findById(employeeInput.getReportsTo())
                    .orElseThrow(() -> new EntityNotFoundException("reportsTo: There is no employee with this id -> "));
        }

        if (!emailEmployeeIgualEmployeeEdit) {
            email = employeesRepository.findByEmail(employeeEdit.getEmail()).isPresent();
        } else {
            employeesCatalogService.salvar(employeeEdit);
            return ResponseEntity.ok(employeeMapper.toModel(employeeEdit));
        }

        if (!email) {
            employeesCatalogService.salvar(employeeEdit);
            return ResponseEntity.ok(employeeMapper.toModel(employeeEdit));
        } else {
            throw new BusinessException("There is already a customer registered with this email");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        if (!employeesRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        employeesCatalogService.excluir(id);

        return ResponseEntity.noContent().build();
    }

}
