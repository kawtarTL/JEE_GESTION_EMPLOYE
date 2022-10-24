package com.example.gesemp.repositories;

import com.example.gesemp.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department getDepartmentByName(String name);
}
