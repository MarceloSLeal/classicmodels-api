package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.OrderDetails;
import com.classicmodels.domain.model.modelId.OrderDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    List<OrderDetails> findByOrderId(Long orderId);

    List<OrderDetails> findByProductId(Long productId);

}
