package com.example.gesemp.repositories;

import com.example.gesemp.models.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole getAppRoleByName(String name);
}
