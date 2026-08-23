package no.tari.canensonic.api.auth;

import jakarta.servlet.http.HttpServletRequest;
import no.tari.canensonic.api.auth.token.APIToken;
import no.tari.canensonic.api.auth.token.APITokenAuth;
import no.tari.canensonic.api.auth.token.APITokenRepository;
import no.tari.canensonic.api.user.CanensUserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final APITokenRepository apiTokenRepository;

    public AuthenticationService(APITokenRepository apiTokenRepository) {
        this.apiTokenRepository = apiTokenRepository;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getParameter("apiKey");
        Optional<APIToken> token = apiTokenRepository.findAPITokenByToken(apiKey);

        if (token.isEmpty()) {
            throw new BadCredentialsException("Invalid API Key");
        }
        else if(token.get().getExpirationDate().isBefore(LocalDateTime.now())){
            throw new BadCredentialsException("API Key Has Expired");
        }

        // TODO, refactor this to allow for fine grained access control.
        return new APITokenAuth(apiKey,List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

}
