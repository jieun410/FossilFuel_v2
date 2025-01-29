package dcu.fossilfuel.gate.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "guestbook")
@Getter
@Setter
@NoArgsConstructor
public class Guestbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}

//	•	@Entity → JPA 엔티티 선언
//	•	@Table(name = "guestbook") → MySQL에서 테이블 이름을 guestbook으로 설정
//	•	@Id, @GeneratedValue(strategy = GenerationType.IDENTITY) → id를 자동 증가 (AUTO_INCREMENT)
//	•	@Column(nullable = false, length = 500) → content 필드는 최대 500자 제한
//	•	@Column(nullable = false, updatable = false) → createdAt은 자동 생성 후 수정 불가