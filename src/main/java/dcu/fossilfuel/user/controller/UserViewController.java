package dcu.fossilfuel.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/login/find-password")
    public String findPassword() {
        return "user/find-password";
    }

    @GetMapping("/login/find-id")
    public String findId() {
        return "user/find-id";
    }
}
