package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Integer> {

    Optional<Customers> findByCustomerEmail(String customerEmail);

}
