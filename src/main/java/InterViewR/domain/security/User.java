package InterViewR.domain.security;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@Builder
@Table(name = "AppUser")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
