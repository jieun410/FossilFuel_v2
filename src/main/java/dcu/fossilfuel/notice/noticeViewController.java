package dcu.fossilfuel.notice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class noticeViewController {

    @GetMapping("/dashboard")
    public String notice() {
        return "notice";
    }
}
