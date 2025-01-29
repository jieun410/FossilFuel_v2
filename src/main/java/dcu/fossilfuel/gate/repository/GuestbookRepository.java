package dcu.fossilfuel.gate.repository;

import dcu.fossilfuel.gate.domain.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {
}

// 	•	JpaRepository<Guestbook, Long>을 상속하여 기본 CRUD 기능 제공