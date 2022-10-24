package com.example.gesemp.services;


import com.example.gesemp.models.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getEmployees();
    Employee getEmployee(Long id);
    Employee addEmployee(Employee employee);
    Employee editEmployee(Long id, Employee employee);
    Optional<String> deleteEmployee(Long id);
    String addRoleToEmployee(String employeeName, String appRoleName);
    String removeRoleFromEmployee(String employeeName, String appRoleName);
    String addEmployeeToDepartment(String empName, String dptName);
    String removeEmployeeFromDepartment(String empName, String dptName);
    Employee loadEmployeeByEmail(String email);
}
