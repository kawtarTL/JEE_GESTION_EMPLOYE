package com.example.gesemp.servicesImpl;

import com.example.gesemp.models.AppRole;
import com.example.gesemp.models.Department;
import com.example.gesemp.models.Employee;
import com.example.gesemp.repositories.AppRoleRepository;
import com.example.gesemp.repositories.DepartmentRepository;
import com.example.gesemp.repositories.EmployeeRepository;
import com.example.gesemp.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AppRoleRepository appRoleRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployee(Long id) {

        return employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Cet employé n'existe pas"));
    }

    @Override
    public Employee addEmployee(Employee employee) {

        String email= employee.getEmail();
        String pw= employee.getPassword();
        boolean exist = employeeRepository.findEmployeeByEmail(email).isPresent();

        if(!exist)
            employee.setPassword(passwordEncoder.encode(pw));
        else
            throw new RuntimeException("L'adresse email "+email+" existe déjà dans le système");

        return employeeRepository.save(employee);
    }

    @Override
    public Employee editEmployee(Long id, Employee employee) {
        return employeeRepository.findById(id)
                .map(
                        (employee1)->{
                            employee1.setFirstName(employee.getFirstName());
                            employee1.setLastName(employee.getLastName());
                            employee1.setEmail(employee.getEmail());
                            employee1.setBirthDate(employee.getBirthDate());
                            employee1.setImgProfil(employee.getImgProfil());
                            employee1.setSalary(employee.getSalary());
                            employee1.setStartWork(employee.getStartWork());
                            employee1.setGenre(employee.getGenre());
                            employee1.setType(employee.getType());

                            return employeeRepository.save(employee1);
                        }).orElseThrow(
                        ()-> new RuntimeException("Employé non trouvé"));
    }

    @Override
    public Optional<String> deleteEmployee(Long id) {
        return Optional.ofNullable(employeeRepository.findById(id)
                .map((employee) -> {
                    employeeRepository.delete(employee);
                    return "Employé supprimé avec succès";
                }).orElseThrow(() -> new RuntimeException("Employé inexistant")));
    }

    @Override
    public String addRoleToEmployee(String employeeName, String appRoleName) {

        Employee employee = employeeRepository.getEmployeeByFirstName(employeeName);
        AppRole role = appRoleRepository.getAppRoleByName(appRoleName);
        employee.getRoles().add(role);

        return "Rôle "+appRoleName+" attribué avec succès à "+employeeName;
    }

    @Override
    public String removeRoleFromEmployee(String employeeName, String appRoleName) {

        AppRole role= appRoleRepository.getAppRoleByName(appRoleName);

        return Optional.of(employeeRepository.getEmployeeByFirstName(employeeName))
                .map(employee -> {
                    employee.getRoles().remove(role);
                    return "L'employé "+employeeName+" n'est plus "+appRoleName ;
                }).orElseThrow(()->new RuntimeException("Cet employé n'existte pas !"));
    }

    @Override
    public String addEmployeeToDepartment(String empName, String dptName) {

        Department department= departmentRepository.getDepartmentByName(dptName);

        return Optional.of(employeeRepository.getEmployeeByFirstName(empName))
                .map(employee -> {
                    employee.setDepartment(department);
                    return "Employé "+empName+" Ajouté au département "+dptName;
                }).orElseThrow(()->new RuntimeException("Cet employé n'existte pas"));
    }

    @Override
    public String removeEmployeeFromDepartment(String empName, String dptName) {

        Department department= departmentRepository.getDepartmentByName(dptName);

        return Optional.of(employeeRepository.getEmployeeByFirstName(empName))
                .map(employee -> {
                    department.getEmployees().remove(employee);

                    return "L'employé "+empName+" ne fait plus partir du département "+dptName;
                }).orElseThrow(()->new RuntimeException("Cet employé n'existe pas"));
    }

    @Override
    public Employee loadEmployeeByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email)
                .orElseThrow(()->new RuntimeException("email incorrect"));
    }
}
