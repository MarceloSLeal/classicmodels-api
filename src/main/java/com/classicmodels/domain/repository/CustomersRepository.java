package com.classicmodels.domain.repository;

import com.classicmodels.api.model.lists.interfaces.CustomersIdNameCreditLimitProjection;
import com.classicmodels.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String customerEmail);

    @Query( value = "SELECT id, name, credit_limit as creditLimit FROM classicmodels_api.customers", nativeQuery = true)
    List<CustomersIdNameCreditLimitProjection> findIdNameCreditLimit();

}