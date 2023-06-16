package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Integer> {
}
