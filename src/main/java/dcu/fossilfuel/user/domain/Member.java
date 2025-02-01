package dcu.fossilfuel.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "members")
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 10)
    private String grade;  // "1학년", "2학년" 등

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // 프로필 이미지는, 회원가입 시 NULL -> 마이페이지에서 설정하도록 [default]
    @Lob
    @Column(name = "profile_image", columnDefinition = "LONGBLOB")
    private byte[] profileImage;

    @Column(name = "profile_url")
    private String profileUrl;

    @Builder
    public Member(String email, String password, String nickname, String grade, Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.grade = grade;
        this.role = role;
    }

    // [ MyPage function & ID/PW finder ]
    public String getProfileImageAsBase64() {
        if (profileImage != null) {
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(profileImage);
        }
        return null;
    }

    public void setProfileImage(byte[] profileImage, String profileUrl) {
        this.profileImage = profileImage;
        this.profileUrl = profileUrl;
    }

    public Member update(String nickname) {
        this.nickname = nickname;
        return this;
    }

    @Transactional
    public Member updatePW(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 비어 있을 수 없습니다.");
        }
        this.password = password;
        return this;
    }

    // Spring Security (Not default Options)✅ 사용자 역할을 `GrantedAuthority` 형태로 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}