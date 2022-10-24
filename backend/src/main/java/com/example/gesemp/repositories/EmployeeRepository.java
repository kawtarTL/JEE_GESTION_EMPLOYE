package com.example.gesemp.repositories;

import com.example.gesemp.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findEmployeeByEmail(String email);
    Employee getEmployeeByFirstName(String firstName);
}
