package no.tari.canensonic.config.filter;

import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.tari.canensonic.api.auth.AuthenticationService;
import no.tari.canensonic.utils.ErrorWriterUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ApiKeyAuthenticationFilter extends GenericFilterBean {

    private final AuthenticationService authenticationService;

    public ApiKeyAuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            if (httpServletRequest.getParameter("apiKey") != null){
                Authentication authentication = authenticationService.getAPIKeyAuthentication((HttpServletRequest) request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
            else{
                filterChain.doFilter(request, response);
            }

        }
        catch (BadCredentialsException bce){
            ErrorWriterUtils.writeErrorElementToWriter(httpResponse,mapper,44);
        }
        catch (Exception exp) {
            ErrorWriterUtils.writeErrorElementToWriter(httpResponse,mapper,0);
            throw new RuntimeException(exp);
        }
    }
}