package com.classicmodels.domain.repository.charts;

import com.classicmodels.api.model.charts.PieChartProjection;
import com.classicmodels.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PieChartRepository extends JpaRepository<Customer, Long> {

    @Query( value="SELECT" +
            " c.country as id, c.country as label," +
            " COUNT(o.id) AS totalOrdered" +
            " FROM" +
            " orders o" +
            " INNER JOIN" +
            " customers c ON o.customer_id = c.id" +
            " WHERE status= 'SHIPPED'" +
            " GROUP BY" +
            "  c.country", nativeQuery = true)
    List<PieChartProjection> findOrdersByCountry();

}
