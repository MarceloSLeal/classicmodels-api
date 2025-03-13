package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.ProductLines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductLinesRepository extends JpaRepository<ProductLines, String> {

    Optional<ProductLines> findByProductLine(String productLine);

    @Query(value = "SELECT product_line FROM productlines", nativeQuery = true)
    List<String> findProductLineList();

}