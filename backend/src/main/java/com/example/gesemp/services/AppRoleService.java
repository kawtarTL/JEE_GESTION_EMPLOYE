package com.example.gesemp.services;

import com.example.gesemp.models.AppRole;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AppRoleService {
    List<AppRole> getRoles();
    Optional<AppRole> getRole(Long id);
    AppRole AddRole(AppRole appRole) throws IOException;
    AppRole editRole(Long id, AppRole appRole);
    String deleteRole(Long id);
}
