package pl.plenczewski.sza3home.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.plenczewski.sza3home.models.AppUser;
import pl.plenczewski.sza3home.models.AppUserRole;
import pl.plenczewski.sza3home.repository.AppUserRepo;
import pl.plenczewski.sza3home.repository.AppUserRoleRepo;

import java.util.ArrayList;
import java.util.List;

@Component
public class FirstAppUserSetUp {

    private AppUserRepo appUserRepo;

    private PasswordEncoder passwordEncoder;

    private AppUserRoleRepo appUserRoleRepo;

    public FirstAppUserSetUp(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder, AppUserRoleRepo appUserRoleRepo) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.appUserRoleRepo = appUserRoleRepo;



        AppUserRole appUserRole = new AppUserRole();
        appUserRole.setRole("ROLE_USER");
        appUserRoleRepo.save(appUserRole);
        AppUserRole appUserRoleAdmin = new AppUserRole();
        appUserRoleAdmin.setRole("ROLE_ADMIN");
        appUserRoleRepo.save(appUserRoleAdmin);


        AppUser appUser = new AppUser();
        appUser.setUsername("user");
        appUser.setPassword(passwordEncoder.encode("user123"));
        List<AppUserRole> roleslist = new ArrayList<>();
        roleslist.add(appUserRole);
        appUser.setAuthorities(roleslist);
        appUser.setEnabled(true);
        appUserRepo.save(appUser);

        System.out.println("** " + appUser.getAuthorities());

    }
}
