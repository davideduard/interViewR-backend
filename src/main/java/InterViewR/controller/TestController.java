package InterViewR.controller;

import InterViewR.services.aiResponses.AiResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController  {
    private final AiResponseService aiResponseService;

    @PostMapping("/gpt")
    public ResponseEntity<String> gptResponse() {
        return ResponseEntity.ok(aiResponseService.getGptResponse(new ArrayList<String>()));
    }
}