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
public class UserService {

    private static final String ROLE_USER="ROLE_USER";
    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenRepo verificationTokenRepo;
    private MailService mailService;
    private AppUserRoleRepo appUserRoleRepo;


    public UserService(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder, VerificationTokenRepo verificationTokenRepo, MailService mailService, AppUserRoleRepo appUserRoleRepo) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepo = verificationTokenRepo;
        this.mailService = mailService;
        this.appUserRoleRepo = appUserRoleRepo;
    }
    private String GenerateURLToken(HttpServletRequest request){
        String token = UUID.randomUUID().toString();
        return "http://" + request.getServerName() + ":" + request.getServerPort()
                +request.getContextPath()
                +"/verify-token?token="+token;
    }

    public void createUser(AppUser appUser, HttpServletRequest request) throws MessagingException {
        if(appUserRepo.getAppUserByUsername(appUser.getUsername())==null){
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
                +request.getContextPath()
                +"/verify-token?token="+token;
        mailService.sendMail(appUser.getUsername(),"Token", url, false);



    }
    private void addUserRole(AppUser appUser){
        List<AppUserRole> list = (List<AppUserRole>) appUser.getAuthorities();
        list.add(appUserRoleRepo.getFirstByRole(ROLE_USER));
        appUser.setAuthorities(list);
    }

    public void verifyToken(String token) {
        VerificationToken verificationToken = verificationTokenRepo.findByValue(token);
        AppUser appUser = verificationToken.getAppUser();
        appUser.setEnabled(true);
        addUserRole(appUser);
        appUserRepo.save(appUser);
        verificationTokenRepo.deleteById(verificationToken.getId());

    }

}
