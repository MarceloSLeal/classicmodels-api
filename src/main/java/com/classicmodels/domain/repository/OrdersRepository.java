package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.Orders;
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

    @Query("select * from orders where date between :fromDate and :toDate")
    List<Orders> findByDate(@Param("fromDate") LocalDateTime fromDate , @Param("toDate") LocalDateTime toDate);

}
