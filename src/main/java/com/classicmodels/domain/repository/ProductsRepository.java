package com.classicmodels.domain.repository;

import com.classicmodels.api.model.lists.interfaces.ProductsIdNameQuantityInStockProjection;
import com.classicmodels.domain.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

    Optional<Products> findByName(String name);
    List<Products> findByProductLine(String productLine);
    List<Products> findByScale(String scale);
    List<Products> findByVendor(String vendor);

    @Query( value = "SELECT id, name, quantity_in_stock as quantityInStock, msrp FROM classicmodels_api.products", nativeQuery = true)
    List<ProductsIdNameQuantityInStockProjection> findIdNameQuantityInStock();

}
