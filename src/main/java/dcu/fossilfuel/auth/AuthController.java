package dcu.fossilfuel.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/error-message")
    public ResponseEntity<String> getErrorMessage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String message = (String) session.getAttribute("errorMessage");
        session.removeAttribute("errorMessage"); // 가져온 후 삭제
        return ResponseEntity.ok(message != null ? message : "");
    }
}
