package com.example.gesemp.controllers;

import com.example.gesemp.models.AppRole;
import com.example.gesemp.models.Employee;
import com.example.gesemp.servicesImpl.AppRoleServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("api/v1/roles")
public class AppRoleController {

    AppRoleServiceImpl appRoleService;

    @GetMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public List<AppRole> getAllRoles(){
        return appRoleService.getRoles();
    }

    @GetMapping("{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Optional<AppRole> getSingleRole(@PathVariable Long id){
        return appRoleService.getRole(id);
    }

    @PostMapping("add")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void addNewRole(@RequestBody AppRole appRole, HttpServletResponse response) throws IOException {
        try{
            AppRole role= appRoleService.AddRole(appRole);
            response.setStatus(HttpServletResponse.SC_OK);
            Map<String, AppRole> body= new HashMap<>();
            body.put("role", role);
            new ObjectMapper().writeValue(response.getOutputStream(), body);
        }
        catch(Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Map<String, String> error= new HashMap<>();
            error.put("mesage", "Ce role existe déjà");
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }

    @PutMapping("edit/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppRole editRole(@PathVariable Long id, @RequestBody AppRole appRole){
        return appRoleService.editRole(id, appRole);
    }

    @DeleteMapping("delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String deleteRole(@PathVariable Long id){
        return appRoleService.deleteRole(id);
    }
}
