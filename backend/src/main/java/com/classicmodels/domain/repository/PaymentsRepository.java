package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, UUID> {

    List<Payments> findByOrderId(Long orderId);
    Optional<Payments> findByCheckNumber(UUID checkNumber);

}
