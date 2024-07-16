package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeesRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    @Query(value = "SELECT id FROM classicmodels_api.employees WHERE job_title = 'Sales Rep'", nativeQuery = true)
    List<Long> findIdsByJobTitleSalesRep();

}
