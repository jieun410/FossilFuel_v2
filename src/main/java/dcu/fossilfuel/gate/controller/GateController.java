package dcu.fossilfuel.gate.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GateController {
    // REST ful 설계 원칙 :
    // - URL 경로에서는 자원을 나타내는 명사를 사용합니다. * 동사를 사용하지 않습니다.

    @Value("${kakao.map.api.key}") // API Key 가져오기
    private String kakaoApiKey;

    @GetMapping("/")
    public String gate(Model model) {
        model.addAttribute("kakaoApiKey", kakaoApiKey); // Thymeleaf에 API Key 전달
        return "gate/gate";
    }

    @GetMapping("/chatbot")
    public String ai() {
        return "gate/chatbot";
    }

    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }



}

