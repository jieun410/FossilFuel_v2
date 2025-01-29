package dcu.fossilfuel.gate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GateController {

    @GetMapping("/")
    public String gate() {
        return "gate/gate";
    }



}

