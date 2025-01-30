package dcu.fossilfuel.gate.controller.ai;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AiChatResponse {
    private String reply;

    public AiChatResponse(String reply) {
        this.reply = reply;
    }


}
