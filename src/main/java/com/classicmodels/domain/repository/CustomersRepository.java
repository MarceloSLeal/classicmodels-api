package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String customerEmail);

}
