package com.example.gesemp.servicesImpl;

import com.example.gesemp.models.AppRole;
import com.example.gesemp.repositories.AppRoleRepository;
import com.example.gesemp.services.AppRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AppRoleServiceImpl implements AppRoleService {

    private final AppRoleRepository appRoleRepository;

    @Override
    public List<AppRole> getRoles() {
        return appRoleRepository.findAll();
    }

    @Override
    public Optional<AppRole> getRole(Long id) {
        return appRoleRepository.findById(id);
    }

    @Override
    public AppRole AddRole(AppRole appRole) {
        AppRole appRole1=null;
        if(appRoleRepository.getAppRoleByName(appRole.getName()) != null) {
            appRole1=appRoleRepository.getAppRoleByName(appRole.getName());
            appRoleRepository.delete(appRole1);
        }
        appRole1= appRoleRepository.save(appRole);
        return appRole1;
    }

    @Override
    public AppRole editRole(Long id, AppRole appRole) {

        return appRoleRepository.findById(id)
                .map(e -> {
                            e.setName(appRole.getName());
                            return e;
                        }).orElseThrow(()->new RuntimeException("Ce role n'existe pas"));
    }

    @Override
    public String deleteRole(Long id) {
        return appRoleRepository.findById(id)
                .map((e)->{
                    appRoleRepository.delete(e);
                    return ("Role supprimé avec succès");
                }).orElseThrow(()->new RuntimeException("Ce role n'existe pas"));
    }
}
