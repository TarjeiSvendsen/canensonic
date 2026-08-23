package no.tari.canensonic.config.filter;

import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.tari.canensonic.api.auth.AuthenticationService;
import no.tari.canensonic.api.base.responses.SubsonicError;
import no.tari.canensonic.api.base.responses.SubsonicRootElement;
import org.springframework.http.MediaType;
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
            if (httpServletRequest.getParameter("apiKey") == null && httpServletRequest.getParameter("u") == null){
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                SubsonicRootElement responseElement = new SubsonicRootElement();
                responseElement.setStatus("failed");
                responseElement.addChildNode("error", SubsonicError.from(10));
                mapper.writeValue(httpResponse.getWriter(),responseElement);
            }
            else if (httpServletRequest.getParameter("u") != null){
                filterChain.doFilter(request, response);
            }
            else {
                Authentication authentication = authenticationService.getAuthentication((HttpServletRequest) request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }

        }
        catch (BadCredentialsException bce){
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            SubsonicRootElement responseElement = new SubsonicRootElement();
            responseElement.setStatus("failed");
            responseElement.addChildNode("error", SubsonicError.from(44));
            mapper.writeValue(httpResponse.getWriter(),responseElement);
        }
        catch (Exception exp) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            SubsonicRootElement responseElement = new SubsonicRootElement();
            responseElement.setStatus("failed");
            responseElement.addChildNode("error", SubsonicError.from(0));
            mapper.writeValue(httpResponse.getWriter(),responseElement);
            throw new RuntimeException(exp);
        }
    }
}