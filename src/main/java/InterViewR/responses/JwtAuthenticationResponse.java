package InterViewR.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
}