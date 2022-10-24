package com.example.gesemp.security.auth;

import com.example.gesemp.models.Employee;
import com.example.gesemp.servicesImpl.EmployeeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class EmployeeDetailsService implements UserDetailsService {

    private final EmployeeServiceImpl employeeService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee= employeeService.loadEmployeeByEmail(email);
        Collection<GrantedAuthority> authorities= new ArrayList<>();
        employee.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        });
        authorities.add(new SimpleGrantedAuthority("USER"));
        return new User(employee.getEmail(), employee.getPassword(), authorities);
    }
}
