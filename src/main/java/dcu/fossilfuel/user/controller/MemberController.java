package dcu.fossilfuel.user.controller;

import dcu.fossilfuel.user.controller.dto.RegisterRequest;
import dcu.fossilfuel.user.service.MailService;
import dcu.fossilfuel.user.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MailService registerMail;

    // 인증 코드를 생성하고 세션에 저장하는 컨트롤러 예제
    @PostMapping("/api/send-verification-code")
    public ResponseEntity<String> sendVerificationCode(HttpSession session, @RequestParam(name = "email") String email) throws Exception {


        String verificationCode = registerMail.sendSimpleMessage(email); // 인증 코드 생성 로직
        session.setAttribute("verificationCode", verificationCode);
        System.out.println("사용자에게 발송한 인증코드 ==> " + verificationCode);

        // 이메일 발송 로직 (생략)
        return ResponseEntity.ok("인증 코드가 이메일로 발송되었습니다.");
    }

    // 인증 코드를 확인하는(1차) 컨트롤러 예제
    @PostMapping("/api/verify-code")
    public ResponseEntity<String> verifyCode(HttpSession session, @RequestParam String code) {
        String verificationCode = (String) session.getAttribute("verificationCode");

        if (verificationCode != null && verificationCode.equals(code)) {
            session.setAttribute("emailVerified", true);
            return ResponseEntity.ok("이메일 인증 성공!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 코드가 일치하지 않습니다.");
        }
    }

    // ======= sign up 과정 중에 진행되는 ========

    // 이메일 중복 체크 API
    @GetMapping("/api/members/check-email")
    public ResponseEntity<String> checkEmailDuplicate(@RequestParam String email) {
        if (memberService.isEmailDuplicate(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 가입된 이메일입니다.");
        }
        return ResponseEntity.ok("사용 가능한 이메일입니다.");
    }


    // 세션에 저장된 이메일과 2차 비교
    @GetMapping("/api/email-last-verified")
    public ResponseEntity<Boolean> checkEmailVerified(HttpSession session) {
        Boolean emailVerified = (Boolean) session.getAttribute("emailVerified");
        if (emailVerified != null && emailVerified) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }


    // 회원 등록
    @PostMapping("/api/members/register")
    public ResponseEntity<?> saveMember(@RequestBody RegisterRequest registerRequest) {
        memberService.saveMember(registerRequest);  // 회원 저장
        return new ResponseEntity<>(HttpStatus.OK);  // 200 OK만 반환
    }


    // [id 찾기 ]
    // 닉네임을 입력받아, 기존 회원 디비와 대조
    @PostMapping("/api/auth/find-id")
    public ResponseEntity<Map<String, Object>> findId(@RequestBody RegisterRequest request) {
        String email = memberService.findEmailByNickname(request.getNickname());

        Map<String, Object> response = new HashMap<>();
        if (email == null) {
            response.put("success", false);
            response.put("message", "닉네임이 잘못되었거나 회원이 아닙니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response.put("success", true);
        response.put("email", email);
        return ResponseEntity.ok(response);
    }




}

