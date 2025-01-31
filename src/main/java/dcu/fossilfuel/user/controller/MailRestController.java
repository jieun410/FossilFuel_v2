package dcu.fossilfuel.user.controller;

import dcu.fossilfuel.user.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/mail")
public class MailRestController {

    @Autowired
    MailService registerMail;

    @PostMapping(value = "/confirm.json")
    public String mailConfirm(@RequestParam(name = "email") String email) throws Exception{

        String code = registerMail.sendSimpleMessage(email);

        System.out.println("사용자에게 발송한 인증코드 ==> " + code);

        return code;
    }

}
