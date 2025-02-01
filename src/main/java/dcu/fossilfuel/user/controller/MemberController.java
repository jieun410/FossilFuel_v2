package dcu.fossilfuel.user.controller;

import dcu.fossilfuel.user.controller.dto.RegisterRequest;
import dcu.fossilfuel.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    // 이메일 중복 체크 API
    @GetMapping("/api/members/check-email")
    public ResponseEntity<String> checkEmailDuplicate(@RequestParam String email) {
        if (memberService.isEmailDuplicate(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 가입된 이메일입니다.");
        }
        return ResponseEntity.ok("사용 가능한 이메일입니다.");
    }


    // @RequestBody 안쓰면 json 데이터 못 받음
    @PostMapping("/api/members/register")
    public String saveMember(@RequestBody RegisterRequest registerRequest) {
        memberService.saveMember(registerRequest);
        return "redirect:/login"; // 회원가입 후 로그인 페이지로 이동
    }




}

