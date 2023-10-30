package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.Orders;
import com.classicmodels.domain.model.OrdersStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

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

}
