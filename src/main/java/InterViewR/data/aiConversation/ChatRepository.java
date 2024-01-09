package InterViewR.data.aiConversation;

import InterViewR.domain.aiConversation.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

}
