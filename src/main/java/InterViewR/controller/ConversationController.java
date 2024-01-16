package InterViewR.controller;

import InterViewR.domain.aiConversation.Message;
import InterViewR.requests.SendMessageRequest;
import InterViewR.responses.SendMessageResponse;
import InterViewR.services.aiResponses.AiResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ConversationController {
    private final AiResponseService aiResponseService;
    @PostMapping("/gptmessage")
    private ResponseEntity<SendMessageResponse> sendMessage(@RequestBody SendMessageRequest request) {
        return ResponseEntity.ok(aiResponseService.sendGptMessage(request));
    }
}
