package InterViewR.data.aiConversation;

import InterViewR.domain.aiConversation.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

}
