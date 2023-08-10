package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.Offices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfficesRepository extends JpaRepository<Offices, Long> {
}
