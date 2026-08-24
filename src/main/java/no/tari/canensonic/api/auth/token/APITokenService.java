package no.tari.canensonic.api.auth.token;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class APITokenService {

    private final APITokenRepository tokenRepository;


    public APITokenService(APITokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Optional<APIToken> getApiTokenByTokenName(String token){
        return tokenRepository.findAPITokenByToken(token);
    }

}
