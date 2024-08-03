package com.classicmodels.domain.repository;

import com.classicmodels.api.model.lists.interfaces.EmployeeIdNameProjection;
import com.classicmodels.domain.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeesRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    @Query(value = "SELECT id, last_name AS lastName, first_name AS firstName, job_title AS jobTitle FROM classicmodels_api.employees " +
            "WHERE job_title = 'Sales Rep'", nativeQuery = true)
    List<EmployeeIdNameProjection> findIdsByJobTitleSalesRep();

    @Query(value =
    "SELECT id, last_name AS lastName, first_name AS firstName, job_title AS jobTitle FROM classicmodels_api.employees " +
    "WHERE job_title = 'President' OR job_title = 'VP SALES' OR job_title = 'VP Marketing' OR job_title = 'Sales Manager (APAC)' " +
    "OR job_title = 'Sales Manager (EMEA)' OR job_title = 'Sales Manager (NA)'", nativeQuery = true)
    List<EmployeeIdNameProjection> findIdName();

}
