package InterViewR.data.aiConversation;

import InterViewR.domain.aiConversation.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}
