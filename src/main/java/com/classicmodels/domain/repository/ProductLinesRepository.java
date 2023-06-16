package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.ProductLines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLinesRepository extends JpaRepository<ProductLines, String> {
}
