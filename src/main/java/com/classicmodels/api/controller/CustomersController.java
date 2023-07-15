package com.classicmodels.api.controller;

import com.classicmodels.api.model.CustomerRepModel;
import com.classicmodels.domain.exception.BusinessException;
import com.classicmodels.domain.model.Customer;
import com.classicmodels.domain.repository.CustomersRepository;
import com.classicmodels.domain.service.CustomerCatalogService;
import com.classicmodels.domain.service.EmployeesCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomersController {

    private CustomersRepository customersRepository;
    private CustomerCatalogService customerCatalogService;

    private EmployeesCatalogService employeesCatalogService;

    @GetMapping
    public List<Customer> listar() {
        return customersRepository.findAll();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerRepModel> buscarPorId(@PathVariable Long customerId) {

        return customersRepository.findById(customerId)
                .map(customer -> {

                    Long employeeId = customer.getEmployee() != null ? customer.getEmployee().getId() : null;

                    CustomerRepModel customerRepModel = new CustomerRepModel();
                    customerRepModel.setId(customer.getId());
                    customerRepModel.setEmail(customer.getEmail());
                    customerRepModel.setName(customer.getName());
                    customerRepModel.setContactLastName(customer.getContactLastName());
                    customerRepModel.setContactFirstName(customer.getContactFirstName());
                    customerRepModel.setPhone(customer.getPhone());
                    customerRepModel.setAddressLine1(customer.getAddressLine1());
                    customerRepModel.setAddressLine2(customer.getAddressLine2());
                    customerRepModel.setCity(customer.getCity());
                    customerRepModel.setState(customer.getState());
                    customerRepModel.setPostalCode(customer.getPostalCode());
                    customerRepModel.setCountry(customer.getCountry());
                    customerRepModel.setCreditLimit(customer.getCreditLimit());
                    customerRepModel.setEmployeeId(employeeId);

                    return ResponseEntity.ok(customerRepModel);
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findbyemail/{customerEmail}")
    public ResponseEntity<Customer> buscarPorEmail(@PathVariable String customerEmail) {

        return customersRepository.findByEmail(customerEmail)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer adicionar(@Valid @RequestBody Customer customer) {
        return customerCatalogService.salvar(customer);
    }

    @PutMapping("/{customerNumber}")
    public ResponseEntity<Customer> atualizar(@PathVariable Long customerNumber, @Valid @RequestBody Customer customer) {

        boolean customerEmail = customersRepository.findByEmail(customer.getEmail()).isEmpty();

        if (!customersRepository.existsById(customerNumber)) {
            return ResponseEntity.notFound().build();
        }

        if (!customerEmail) {
            throw new BusinessException("There is already a customer registered with this email");
        }

        customer.setId(customerNumber);
        customer = customerCatalogService.salvar(customer);

        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{customerNumber}")
    public ResponseEntity<Void> excluir(@PathVariable Long customerNumber) {

        if(!customersRepository.existsById(customerNumber)) {
            return ResponseEntity.notFound().build();
        }

        customerCatalogService.excluir(customerNumber);

        return ResponseEntity.noContent().build();
    }

}
