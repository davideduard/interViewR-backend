package InterViewR.domain.aiConversation;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@Builder
@Table(name = "Message")
public class Message {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int Id;

    @ManyToOne
    private Chat chat;

    private String message;

    private int count;
}
