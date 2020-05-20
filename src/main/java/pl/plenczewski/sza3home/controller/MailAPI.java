package pl.plenczewski.sza3home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.plenczewski.sza3home.services.MailService;

import javax.mail.MessagingException;

@Controller
public class MailAPI {
    private MailService mailService;

    public MailAPI(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/mail")
    public String sendMail() throws MessagingException {
        mailService.sendMail("p.lenczewski94@gmail.com",
                "Wygrałeś",
                "<b>1000 000 zł</b><br>:P", true);
        return "login";
    }
}
