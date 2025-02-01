package dcu.fossilfuel.user.service;

import dcu.fossilfuel.user.controller.dto.RegisterRequest;
import dcu.fossilfuel.user.domain.Member;
import dcu.fossilfuel.user.domain.Role;
import dcu.fossilfuel.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public boolean isEmailDuplicate(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }


    public void saveMember(RegisterRequest dto) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // 이메일 중복체크
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Member member = Member.builder()
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .grade(dto.getGrade())
                .role(Role.ROLE_USER)

                .build();

        memberRepository.save(member);
    }
}
