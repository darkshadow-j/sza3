package pl.plenczewski.sza3home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.plenczewski.sza3home.models.AppUser;
import pl.plenczewski.sza3home.repository.AppUserRepo;
import pl.plenczewski.sza3home.services.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {


    private UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/singup")
    public String registerForm(Model model){
        model.addAttribute("registerUser", new AppUser());
        return "registration";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute AppUser appUser, HttpServletRequest request) throws MessagingException {
        userService.createUser(appUser, request);
        return "registration";
    }

    @RequestMapping("verify-token")
    public String veryfiToken(@RequestParam String token){
        userService.verifyToken(token);
        return "login";
    }
}
