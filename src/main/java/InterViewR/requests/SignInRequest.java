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
public class SignInRequest {
    private String email;
    private String password;
}
