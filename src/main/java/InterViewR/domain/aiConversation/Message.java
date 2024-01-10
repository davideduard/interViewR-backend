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
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Column(columnDefinition = "VARCHAR(5000)")
    private String message;

    private int count;
}
