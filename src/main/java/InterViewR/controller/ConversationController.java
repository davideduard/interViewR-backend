package InterViewR.controller;

import InterViewR.requests.SendMessageRequest;
import InterViewR.services.aiResponses.AiResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@CrossOrigin
public class ConversationController {
    private final AiResponseService aiResponseService;
    @PostMapping("/gptmessage")
    private ResponseEntity<String> sendMessage(@RequestBody SendMessageRequest request) {
        if(request.getChatId() == -1){
            var chat = aiResponseService.createChat();
            request.setChatId(chat.getId());
        }

        return ResponseEntity.ok(aiResponseService.sendGptMessage(request));
    }
}
