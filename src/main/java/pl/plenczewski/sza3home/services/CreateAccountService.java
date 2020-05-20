package pl.plenczewski.sza3home.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.plenczewski.sza3home.models.AppUser;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Service
public class CreateAccountService {

    UserService userService;
    AdminService adminService;

    @Autowired
    public CreateAccountService(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    public void createNewAccount(AppUser appUser, HttpServletRequest request) throws MessagingException {
        if(appUser.isMakeAdmin()){
            System.out.println("CREATE ADMN");
            adminService.createAdmin(appUser, request);
        }
        userService.createUser(appUser,request);
    }


}
