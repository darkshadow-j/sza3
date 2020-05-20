package pl.plenczewski.sza3home.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.plenczewski.sza3home.models.AppUser;
import pl.plenczewski.sza3home.models.AppUserRole;
import pl.plenczewski.sza3home.models.VerificationToken;
import pl.plenczewski.sza3home.repository.AppUserRepo;
import pl.plenczewski.sza3home.repository.AppUserRoleRepo;
import pl.plenczewski.sza3home.repository.VerificationTokenRepo;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    PasswordEncoder passwordEncoder;
    AppUserRepo appUserRepo;
    VerificationTokenRepo verificationTokenRepo;
    MailService mailService;
    AppUserRoleRepo appUserRoleRepo;

    @Autowired
    public AdminService(PasswordEncoder passwordEncoder, AppUserRepo appUserRepo, VerificationTokenRepo verificationTokenRepo, MailService mailService, AppUserRoleRepo appUserRoleRepo) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepo = appUserRepo;
        this.verificationTokenRepo = verificationTokenRepo;
        this.mailService = mailService;
        this.appUserRoleRepo = appUserRoleRepo;
    }

    public void createAdmin(AppUser appUser, HttpServletRequest request) throws MessagingException {
        if (appUserRepo.getAppUserByUsername(appUser.getUsername()) == null) {
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            appUserRepo.save(appUser);
        }

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setAppUser(appUser);
        verificationToken.setValue(token);
        verificationTokenRepo.save(verificationToken);

        String url;
        url = "http://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath()
                + "/admin-token?token=" + token;
        mailService.sendMail("p.lenczewski@um.skierniewice.pl", "Token", url, false);
    }

    public void verifyToken(String token) {
        VerificationToken verificationToken = verificationTokenRepo.findByValue(token);
        AppUser appUser = verificationToken.getAppUser();
        appUser.setEnabled(true);
        addAdminRole(appUser);
        appUserRepo.save(appUser);
        verificationTokenRepo.deleteById(verificationToken.getId());
    }

    private void addAdminRole(AppUser appUser) {
        List<AppUserRole> list = (List<AppUserRole>) appUser.getAuthorities();
        list.add(appUserRoleRepo.getFirstByRole(ROLE_ADMIN));
        appUser.setAuthorities(list);

    }
}
