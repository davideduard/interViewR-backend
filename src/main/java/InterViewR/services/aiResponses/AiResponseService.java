package InterViewR.services.aiResponses;

import InterViewR.requests.EndChatRequest;
import InterViewR.requests.SendMessageRequest;
import InterViewR.responses.EndMessageResponse;
import InterViewR.responses.SendMessageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AiResponseService {
    String getGptGeneratedProfile(List<String> keywords);

    SendMessageResponse sendGptMessage(SendMessageRequest request);
    EndMessageResponse sendFinalGptMessage(EndChatRequest request);
}
