package dcu.fossilfuel.user.controller;

import dcu.fossilfuel.user.service.MailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class MailRestController {

    @Autowired
    MailService registerMail;

    // 인증 코드를 생성하고 세션에 저장하는 컨트롤러 예제
    @PostMapping("/send-verification-code")
    public ResponseEntity<String> sendVerificationCode(HttpSession session, @RequestParam(name = "email") String email) throws Exception {


        String verificationCode = registerMail.sendSimpleMessage(email); // 인증 코드 생성 로직
        session.setAttribute("verificationCode", verificationCode);
        System.out.println("사용자에게 발송한 인증코드 ==> " + verificationCode);

        // 이메일 발송 로직 (생략)
        return ResponseEntity.ok("인증 코드가 이메일로 발송되었습니다.");
    }

    // 인증 코드를 확인하는 컨트롤러 예제
    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(HttpSession session, @RequestParam String code) {
        String verificationCode = (String) session.getAttribute("verificationCode");

        if (verificationCode != null && verificationCode.equals(code)) {
            session.setAttribute("emailVerified", true);
            return ResponseEntity.ok("이메일 인증 성공!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 코드가 일치하지 않습니다.");
        }
    }


}
