package com.example.gesemp.servicesImpl;

import com.example.gesemp.models.Department;
import com.example.gesemp.repositories.DepartmentRepository;
import com.example.gesemp.services.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Optional<Department> getDepartment(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department editDepartment(Long id, Department department) {
        return departmentRepository.findById(id)
                .map(d->{
                    d.setName(department.getName());
                    d.setDescription(department.getDescription());
                    d.setCode(department.getCode());
                    return d;
                }).orElseThrow(()->new RuntimeException("Département non trouvé !"));
    }

    @Override
    public String deleteDepartment(Long id) {
        return departmentRepository.findById(id)
                .map(d->{
                    departmentRepository.delete(d);
                    return "Département supprimé avec succès";
                }).orElseThrow(()->new RuntimeException("Ce département n'existe pas"));
    }
}
