package com.classicmodels.domain.repository.charts;

import com.classicmodels.api.model.charts.BarChartProjection;
import com.classicmodels.domain.model.charts.BarChart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BarChartRepository implements BarChartProjection {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BarChart> findTopCountryProductSales(){
        String sql = """
            WITH TopCountries AS (
                SELECT c.country
                FROM orders o
                INNER JOIN customers c ON o.customer_id = c.id
                GROUP BY c.country
                ORDER BY COUNT(o.id) DESC
                LIMIT 10
            ),
            TopProducts AS (
                SELECT p.id, p.name
                FROM orderdetails od
                INNER JOIN products p ON od.product_id = p.id
                GROUP BY p.id, p.name
                ORDER BY SUM(od.quantity_ordered) DESC
                LIMIT 10
            )
            SELECT tc.country, tp.name AS product_name,
                   COALESCE(SUM(od.quantity_ordered), 0) AS total_quantity
            FROM TopCountries tc
            CROSS JOIN TopProducts tp
            LEFT JOIN customers c ON c.country = tc.country
            LEFT JOIN orders o ON o.customer_id = c.id
            LEFT JOIN orderdetails od ON od.order_id = o.id AND od.product_id = tp.id
            GROUP BY tc.country, tp.name
            ORDER BY tc.country, tp.name
            """;

        List<Object[]> results = entityManager
                .createNativeQuery(sql)
                .getResultList();

        Map<String, BarChart> barChartMap = new LinkedHashMap<>();

        for (Object[] row : results) {
            String country = (String) row[0];
            String product = (String) row[1];
            Integer quantity = ((Number) row[2]).intValue();

            BarChart chart = barChartMap.computeIfAbsent(country, BarChart::new);
            chart.addProduct(product, quantity);
        }

        return new ArrayList<>(barChartMap.values());
    }

}
