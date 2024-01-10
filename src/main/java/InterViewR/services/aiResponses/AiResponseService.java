package InterViewR.services.aiResponses;

import InterViewR.domain.aiConversation.Chat;
import InterViewR.domain.aiConversation.Message;
import InterViewR.requests.SendMessageRequest;
import InterViewR.responses.SendMessageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AiResponseService {
    String getGptGeneratedProfile(List<String> keywords);

    SendMessageResponse sendGptMessage(SendMessageRequest request);
}
