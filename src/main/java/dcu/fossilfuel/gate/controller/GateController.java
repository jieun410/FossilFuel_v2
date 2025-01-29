package dcu.fossilfuel.gate.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GateController {


    @Value("${kakao.map.api.key}") // API Key 가져오기
    private String kakaoApiKey;

    @GetMapping("/")
    public String gate(Model model) {
        model.addAttribute("kakaoApiKey", kakaoApiKey); // Thymeleaf에 API Key 전달
        return "gate/gate";
    }



}

