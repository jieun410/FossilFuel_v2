package dcu.fossilfuel.user.controller.dto;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String nickname;
    private String email;
    private String password;
    private String grade;  // "1학년", "2학년", "3학년", "4학년" 등을 저장



    // Role & ImageURL 은 백엔드 only => 서비스단 처리



}
