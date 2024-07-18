package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.Offices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfficesRepository extends JpaRepository<Offices, Long> {

    @Query(value = "SELECT id FROM classicmodels_api.offices", nativeQuery = true)
    List<Long> findIds();
}
