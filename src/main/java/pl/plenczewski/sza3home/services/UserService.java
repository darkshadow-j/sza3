package pl.plenczewski.sza3home.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.plenczewski.sza3home.models.AppUser;
import pl.plenczewski.sza3home.models.VerificationToken;
import pl.plenczewski.sza3home.repository.AppUserRepo;
import pl.plenczewski.sza3home.repository.VerificationTokenRepo;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class UserService {


    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenRepo verificationTokenRepo;
    private MailService mailService;

    @Autowired
    public UserService(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder, VerificationTokenRepo verificationTokenRepo, MailService mailService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepo = verificationTokenRepo;
        this.mailService = mailService;
    }

    public void createUser(AppUser appUser, HttpServletRequest request) throws MessagingException {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUserRepo.save(appUser);

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

    public void verifyToken(String token) {
        AppUser appUser = verificationTokenRepo.findByValue(token).getAppUser();
        appUser.setEnabled(true);
        appUserRepo.save(appUser);

    }
}
