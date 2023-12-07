package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

    Optional<Products> findByName(String name);
    List<Products> findByProductLine(String productLine);
    List<Products> findByScale(String scale);
    List<Products> findByVendor(String vendor);

}
