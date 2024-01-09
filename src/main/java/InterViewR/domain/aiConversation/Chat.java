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

    private LocalDateTime date;

    @OneToMany
    private List<Message> messageList;
}
