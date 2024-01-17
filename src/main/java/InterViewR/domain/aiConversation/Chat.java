package InterViewR.domain.aiConversation;

import InterViewR.domain.security.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@Builder
@Table(name = "Chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int Id;

    @ManyToOne
    private User user;

    @ElementCollection
    private List<String> keywords;

    private LocalDateTime date;

    @Column(columnDefinition = "VARCHAR(5000)")
    private String identity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chat", orphanRemoval = true)
    private List<Message> messageList;

    public Message insertMessage(String text){
        var message = Message.builder()
                .message(text)
                .chat(this)
                .count(this.messageList.size())
                .build();

        this.messageList.add(message);
        return message;
    }
}
