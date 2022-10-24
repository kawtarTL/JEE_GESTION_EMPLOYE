package com.example.gesemp.services;

import com.example.gesemp.models.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    List<Department> getDepartments();
    Optional<Department> getDepartment(Long id);
    Department addDepartment(Department department);
    Department editDepartment(Long id, Department department);
    String deleteDepartment(Long id);
}
