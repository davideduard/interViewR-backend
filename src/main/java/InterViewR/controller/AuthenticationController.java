package InterViewR.controller;

import InterViewR.requests.EndChatRequest;
import InterViewR.requests.SendMessageRequest;
import InterViewR.requests.SignInRequest;
import InterViewR.requests.SignUpRequest;
import InterViewR.responses.JwtAuthenticationResponse;
import InterViewR.responses.SendMessageResponse;
import InterViewR.security.AuthenticationService;
import InterViewR.services.aiResponses.AiResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    private final AiResponseService aiResponseService;

    @PostMapping("/gptmessage")
    private ResponseEntity<SendMessageResponse> sendMessage(@RequestBody SendMessageRequest request) {
        return ResponseEntity.ok(aiResponseService.sendGptMessage(request));
    }

    @PostMapping("/endmessage")
    private ResponseEntity<String> endMessage(@RequestBody EndChatRequest request) {
        return ResponseEntity.ok(aiResponseService.sendFinalGptMessage(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }


}