package com.classicmodels.domain.repository;

import com.classicmodels.api.model.lists.EmployeeRepModelIdNameList;
import com.classicmodels.api.model.lists.interfaces.EmployeeIdNameProjection;
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

    @Query(value = "SELECT id, last_name as lastName, first_name as firstName, job_title as jobTitle from classicmodels_api.employees", nativeQuery = true)
    List<EmployeeIdNameProjection> findIdName();

}
