package InterViewR.data.aiConversation;

import InterViewR.domain.aiConversation.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}
