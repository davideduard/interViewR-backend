package InterViewR.services.aiResponses;

import InterViewR.domain.aiConversation.Chat;
import InterViewR.requests.SendMessageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AiResponseService {
    String getGptGeneratedProfile(List<String> keywords);

    String sendGptMessage(SendMessageRequest request);

    Chat createChat();
}
