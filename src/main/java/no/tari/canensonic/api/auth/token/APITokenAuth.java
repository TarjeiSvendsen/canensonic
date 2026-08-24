package no.tari.canensonic.api.auth.token;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class APITokenAuth extends AbstractAuthenticationToken {

    private List<? extends GrantedAuthority> authorities;
    private String apiKey;
    private String username;

    public APITokenAuth(String apiKey,String username,@Nullable List<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.authorities = authorities;
        this.apiKey = apiKey;
        this.username = username;
        this.setAuthenticated(true);

    }

    @Override
    public @Nullable Object getCredentials() {
        return apiKey;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return username;
    }
}
