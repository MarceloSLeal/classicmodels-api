package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.Payments;
import com.classicmodels.domain.model.PaymentsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, PaymentsId> {

    List<Payments> findByCustomerId(Long customerId);
    List<Payments> findByCheckNumber(String checkNumber);
    Optional<Payments> findByCustomerIdAndCheckNumber(Long customerId, String checkNumber);

}
