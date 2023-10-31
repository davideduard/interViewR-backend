package InterViewR.services.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
}
