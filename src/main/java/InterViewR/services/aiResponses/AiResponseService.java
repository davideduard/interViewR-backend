package InterViewR.services.aiResponses;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AiResponseService {
    String getGptResponse(List<String> keywords);
}
