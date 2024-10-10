package com.classicmodels.domain.repository;

import com.classicmodels.api.model.lists.interfaces.OrdersIdStatusProjection;
import com.classicmodels.domain.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query(value = "select * from orders where date between :fromDate and :toDate", nativeQuery = true)
    List<Orders> findByDate(@Param("fromDate") String fromDate , @Param("toDate") String toDate);

    @Query(value = "select * from orders where required_date between :fromDate and :toDate", nativeQuery = true)
    List<Orders> findByRequiredDate(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Query(value = "select * from orders where shipped_date between :fromDate and :toDate", nativeQuery = true)
    List<Orders> findByShippedDate(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Query(value = "select * from orders where status = :status", nativeQuery = true)
    List<Orders> findByStatus(@Param("status") String status);

    List<Orders> findByCustomerId(Long id);

    @Query( value = "SELECT id, status from classicmodels_api.orders WHERE status = 'RESOLVED'", nativeQuery = true)
    List<OrdersIdStatusProjection> findIdStatus();

}
