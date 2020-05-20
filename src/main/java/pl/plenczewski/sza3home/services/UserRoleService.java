package pl.plenczewski.sza3home.services;

import org.springframework.stereotype.Service;
import pl.plenczewski.sza3home.models.AppUserRole;
import pl.plenczewski.sza3home.repository.AppUserRoleRepo;

import java.util.List;

@Service
public class UserRoleService {

    AppUserRoleRepo appUserRoleRepo;

    public UserRoleService(AppUserRoleRepo appUserRoleRepo) {
        this.appUserRoleRepo = appUserRoleRepo;
    }

    public List<AppUserRole> getAllRolesInSystem(){
        return appUserRoleRepo.findAll();
    }
}
