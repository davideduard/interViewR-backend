package InterViewR.security;

import InterViewR.requests.SignInRequest;
import InterViewR.requests.SignUpRequest;
import InterViewR.responses.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);
}