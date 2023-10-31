package InterViewR.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}