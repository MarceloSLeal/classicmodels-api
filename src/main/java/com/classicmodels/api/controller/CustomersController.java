package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.CustomerMapper;
import com.classicmodels.api.model.CustomerRepModel;
import com.classicmodels.api.model.input.CustomerInput;
import com.classicmodels.api.model.lists.CustomersRepModelIdNameCreditLimitList;
import com.classicmodels.api.model.lists.interfaces.CustomersIdNameCreditLimitProjection;
import com.classicmodels.domain.exception.BusinessException;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Customer;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.service.CustomerCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "${CONTROLLERS_CROSS_ORIGIN}")
public class CustomersController {

    private CustomersRepository customersRepository;
    private CustomerCatalogService customerCatalogService;
    private CustomerMapper customerMapper;

    @GetMapping
    public List<CustomerRepModel> listar() {

        return customerMapper.toCollectionModel(customersRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerRepModel> buscarPorId(@PathVariable Long id) {

        return customersRepository.findById(id)
                .map(customer -> ResponseEntity.ok(customerMapper.toModel(customer)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/idnamecreditlimit")
    public ResponseEntity<List<CustomersRepModelIdNameCreditLimitList>> buscarPorIdNameCreditLimit() {

        List<CustomersIdNameCreditLimitProjection> projections = customersRepository.findIdNameCreditLimit();
        if (projections.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CustomersRepModelIdNameCreditLimitList> customersIds = projections.stream()
                .map(projection -> {
                    CustomersRepModelIdNameCreditLimitList dto = new CustomersRepModelIdNameCreditLimitList();
                    dto.setId(projection.getId());
                    dto.setName(projection.getName());
                    dto.setCreditLimit(projection.getCreditLimit());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(customersIds);
    }

    @GetMapping("/findbyemail/{email}")
    public ResponseEntity<CustomerRepModel> buscarPorEmail(@PathVariable String email) {

        return customersRepository.findByEmail(email)
                .map(customer -> ResponseEntity.ok(customerMapper.toModel(customer)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerRepModel adicionar(@Valid @RequestBody CustomerInput customerInput) {
        Customer newCustomer = customerMapper.toEntity(customerInput);

        CustomerRepModel customerRepModel = customerMapper.toModel(newCustomer);

        boolean customerEmail = customersRepository.findByEmail(newCustomer.getEmail())
                .stream()
                .anyMatch(customerExists -> !customerExists.equals(newCustomer));

        if (customerEmail) {
            throw new BusinessException("There is already a customer registered with this email");
        }

        customerCatalogService.salvar(newCustomer);

        return customerRepModel;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerRepModel> atualizar(@PathVariable Long id, @Valid @RequestBody CustomerInput customerInput) {

        Customer customer = customersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not exists"));
        Customer customerEdit = customerMapper.toEntity(customerInput);
        customerEdit.setId(id);

        boolean emailCustomerIgualCustomerEdit = customer.getEmail().equals(customerEdit.getEmail());
        boolean email;

        if (!emailCustomerIgualCustomerEdit) {
            email = customersRepository.findByEmail(customerEdit.getEmail()).isPresent();
        } else {
            customerCatalogService.salvar(customerEdit);
            return ResponseEntity.ok(customerMapper.toModel(customerEdit));
        }

        if (!email) {
            customerCatalogService.salvar(customerEdit);
            return ResponseEntity.ok(customerMapper.toModel(customerEdit));
        } else {
            throw new BusinessException("There is already a customer registered with this email");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        if (!customersRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        customerCatalogService.excluir(id);

        return ResponseEntity.noContent().build();
    }

}
