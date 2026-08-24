package no.tari.canensonic.api.auth;

import jakarta.servlet.http.HttpServletRequest;
import no.tari.canensonic.api.auth.token.APIToken;
import no.tari.canensonic.api.auth.token.APITokenAuth;
import no.tari.canensonic.api.auth.token.APITokenRepository;
import no.tari.canensonic.api.user.CanensUser;
import no.tari.canensonic.api.user.CanensUserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService{
    private final APITokenRepository apiTokenRepository;
    private final CanensUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(APITokenRepository apiTokenRepository, CanensUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.apiTokenRepository = apiTokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Authentication getUserAuthentication(HttpServletRequest request){
        Optional<CanensUser> user = userRepository.findCanensUserByUsername(request.getParameter("u"));
        if (user.isEmpty()) throw new BadCredentialsException("Invalid username or password");
        else if (passwordEncoder.matches(request.getParameter("p"),user.get().getPassword())){
            return new UsernamePasswordAuthenticationToken(user.get().getUsername(),user.get().getPassword(),user.get().getAuthoritiesList());
        }
        else throw new BadCredentialsException("Invalid username or password");
    }

    public Authentication getAPIKeyAuthentication(HttpServletRequest request) {
        String apiKey = request.getParameter("apiKey");
        Optional<APIToken> token = apiTokenRepository.findAPITokenByToken(apiKey);

        if (token.isEmpty()) {
            throw new BadCredentialsException("Invalid API Key");
        }
        else if(token.get().getExpirationDate().isBefore(LocalDateTime.now())){
            throw new BadCredentialsException("API Key Has Expired");
        }

        // TODO, refactor this to allow for fine grained access control.
        return new APITokenAuth(token.get().getToken(),token.get().getUsername(),List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

}
