package dcu.fossilfuel.gate.service;

import dcu.fossilfuel.gate.domain.Guestbook;
import dcu.fossilfuel.gate.repository.GuestbookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestbookService {

    private final GuestbookRepository guestbookRepository;

    public GuestbookService(GuestbookRepository guestbookRepository) {
        this.guestbookRepository = guestbookRepository;
    }

    // 방명록 전체 조회
    public List<Guestbook> getAllGuestbookEntries() {
        return guestbookRepository.findAll();
    }

    // 방명록 추가
    public Guestbook addGuestbookEntry(String content) {
        Guestbook entry = new Guestbook();
        entry.setContent(content);
        return guestbookRepository.save(entry);
    }
}

//	•	getAllGuestbookEntries() → 방명록 목록을 불러오는 메서드
//	•	addGuestbookEntry(String content) → 방명록에 새로운 글을 추가하는 메서드