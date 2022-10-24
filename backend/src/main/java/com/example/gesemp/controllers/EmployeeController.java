package com.example.gesemp.controllers;

import com.example.gesemp.models.Employee;
import com.example.gesemp.servicesImpl.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("api/v1/employees")
@AllArgsConstructor
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    @GetMapping("")
    @PostAuthorize("hasAuthority('ADMIN')")
    public List<Employee> getAllEmployees(){
        return employeeService.getEmployees();
    }

    @GetMapping("{id}")
    public Employee getEmployee(@PathVariable Long id, HttpServletResponse response, Authentication authentication) throws IOException{
        Employee employee1= employeeService.getEmployee(id);
        GrantedAuthority ADMIN= new SimpleGrantedAuthority("ADMIN");
        if (employee1.getEmail().equals(authentication.getName()) || authentication.getAuthorities().contains(ADMIN)) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            Map<String, String> error= new HashMap<>();
            error.put("message", "action rejetée");
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
        return employee1;
    }

    @PostMapping("add")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Map<String, Employee> addEmployee(@RequestBody Employee employee){

        Map<String, Employee> map = new HashMap<>();
        Employee employeeObject = employeeService.addEmployee(employee);
        employeeService.addRoleToEmployee(employeeObject.getFirstName(), "USER");
        map.put("Employee", employeeObject);
        return map;
    }

    @PutMapping("edit/{id}")
    public void editEmployee(@RequestBody Employee employee, @PathVariable Long id, HttpServletResponse response, Authentication authentication) throws IOException {
        Employee employee1= employeeService.getEmployee(id);
        Employee edited= null;
        GrantedAuthority ADMIN= new SimpleGrantedAuthority("ADMIN");
        if (employee1.getEmail().equals(authentication.getName()) || authentication.getAuthorities().contains(ADMIN)) {
            edited = employeeService.editEmployee(id, employee);

            response.setStatus(HttpServletResponse.SC_OK);
            Map<String, Employee> body= new HashMap<>();
            body.put("employee", edited);
            new ObjectMapper().writeValue(response.getOutputStream(), body);
        }
        else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            Map<String, String> error= new HashMap<>();
            error.put("message", "action rejetée");
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }

    }

    @DeleteMapping("delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Optional<String> deleteEmployee(@PathVariable Long id){
        return employeeService.deleteEmployee(id);
    }

    @GetMapping("addRoleToEmployee")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String  addRoleToEmployee(@RequestBody EmployeeRole employeeRole){
        return employeeService.addRoleToEmployee(employeeRole.getEmployeeName(), employeeRole.getRoleName());
    }

    @GetMapping("addEmployeeToDepartment")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String addEmployeeToDepartment(@RequestBody EmployeeDepartment employeeDepartment){
        return employeeService.addEmployeeToDepartment(employeeDepartment.getEmployeeName(), employeeDepartment.getDepartmentName());
    }

    @GetMapping("removeEmployeeFromDepartment")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String removeEmployeeFromDepartment(@RequestBody EmployeeDepartment employeeDepartment){
        return employeeService.removeEmployeeFromDepartment(employeeDepartment.getEmployeeName(), employeeDepartment.getDepartmentName());
    }

    @GetMapping("removeRoleFromEmployee")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String removeRoleFromEmployee(@RequestBody EmployeeRole employeeRole){
        return employeeService.removeRoleFromEmployee(employeeRole.getEmployeeName(), employeeRole.getRoleName());
    }
}

@Data
class EmployeeRole {
    private String employeeName;
    private String roleName;
}

@Data
class EmployeeDepartment {
    private String employeeName;
    private String departmentName;
}
