package pl.plenczewski.sza3home.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.plenczewski.sza3home.models.AppUser;
import pl.plenczewski.sza3home.repository.AppUserRepo;

@Component
public class FirstAppUserSetUp {

    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public FirstAppUserSetUp(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;

        AppUser appUser = new AppUser();
        appUser.setUsername("user");
        appUser.setPassword(passwordEncoder.encode("user123"));

        appUserRepo.save(appUser);

    }
}
