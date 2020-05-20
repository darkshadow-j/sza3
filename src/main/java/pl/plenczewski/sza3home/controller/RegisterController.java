package pl.plenczewski.sza3home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.plenczewski.sza3home.models.AppUser;
import pl.plenczewski.sza3home.repository.AppUserRepo;
import pl.plenczewski.sza3home.services.AdminService;
import pl.plenczewski.sza3home.services.CreateAccountService;
import pl.plenczewski.sza3home.services.UserRoleService;
import pl.plenczewski.sza3home.services.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {


    private UserService userService;
    private UserRoleService userRoleService;
    private AdminService adminService;
    private CreateAccountService createAccountService;

    @Autowired
    public RegisterController(UserService userService, UserRoleService userRoleService, AdminService adminService, CreateAccountService createAccountService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.adminService = adminService;
        this.createAccountService = createAccountService;
    }

    @GetMapping("/singup")
    public String registerForm(Model model){
        model.addAttribute("registerUser", new AppUser());
        model.addAttribute("allRoles", userRoleService.getAllRolesInSystem());
        return "registration";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute AppUser appUser, HttpServletRequest request) throws MessagingException {
        createAccountService.createNewAccount(appUser, request);
        return "registration";
    }

    @RequestMapping("verify-token")
    public String veryfiToken(@RequestParam String token){
        userService.verifyToken(token);
        return "login";
    }

    @RequestMapping("admin-token")
    public String veryfiAdminToken(@RequestParam String token){
        adminService.verifyToken(token);
        return "login";
    }
}
