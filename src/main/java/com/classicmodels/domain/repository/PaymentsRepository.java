package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.Payments;
import com.classicmodels.domain.model.PaymentsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, PaymentsId> {
}
