package com.example.gesemp.controllers;
import com.example.gesemp.models.Department;
import com.example.gesemp.services.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/departments")
@CrossOrigin("*")
public class DepartmentController {

    private final DepartmentService departmentService;
 
    @GetMapping()
    public List<Department> getAllDepartments(){
        return departmentService.getDepartments();
    }

    public DepartmentController(DepartmentService departmentService) {
		super();
		this.departmentService = departmentService;
	}

	@GetMapping("{id}")
    public Optional<Department> getSingleDepartment(@PathVariable Long id){
        return departmentService.getDepartment(id);
    }

    @PostMapping("add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Department addNewDepartment(@RequestBody Department department){
        return departmentService.addDepartment(department);
    }

    @PutMapping("edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Department editDepartment(@PathVariable Long id, @RequestBody Department department){
        return departmentService.editDepartment(id, department);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteDepartment(@PathVariable Long id){
        return departmentService.deleteDepartment(id);
    }
}
