package no.tari.canensonic.api.system;

import no.tari.canensonic.api.auth.token.APIToken;
import no.tari.canensonic.api.auth.token.APITokenService;
import no.tari.canensonic.api.auth.token.TokenInfoElement;
import no.tari.canensonic.api.base.responses.SubsonicError;
import no.tari.canensonic.api.base.responses.SubsonicRootElement;
import no.tari.canensonic.utils.OpenSubsonicExtensionUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Optional;

@RestController
public class SystemController {

    private final ObjectMapper mapper;
    private final APITokenService tokenService;

    public SystemController(ObjectMapper mapper, APITokenService tokenService) {
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    // -- /rest/ping

    @GetMapping(value = {"/rest/ping","/rest/ping.view"},produces = "application/json")
    ResponseEntity<String> pingServerGet(){
        return pingServer();
    }

    @PostMapping(value = {"/rest/ping","/rest/ping.view"},produces = "application/json")
    ResponseEntity<String> pingServerPost(){
        return pingServer();
    }

    ResponseEntity<String> pingServer(){
        SubsonicRootElement rootElement = new SubsonicRootElement();
        try {
            rootElement.setStatus("ok");
            return new ResponseEntity<>(mapper.writeValueAsString(rootElement), HttpStatusCode.valueOf(200));
        }catch (Exception e){
            rootElement.setStatus("failed");
            rootElement.addChildNode("error", SubsonicError.from(0));
            return new ResponseEntity<>(mapper.writeValueAsString(rootElement), HttpStatusCode.valueOf(200));
        }
    }

    // -- rest/getOpenSubsonicExtensions --

    @GetMapping(value = {"/rest/getOpenSubsonicExtensions","/rest/getOpenSubsonicExtensions.view"},produces = "application/json")
    ResponseEntity<String> openSubsonicExtensionsGet(){
        return getOpenSubsonicExtensions();
    }

    @PostMapping(value = {"/rest/getOpenSubsonicExtensions","/rest/getOpenSubsonicExtensions.view"},produces = "application/json")
    ResponseEntity<String> openSubsonicExtensionsPost(){
        return getOpenSubsonicExtensions();
    }

    ResponseEntity<String> getOpenSubsonicExtensions(){
        SubsonicRootElement rootElement = new SubsonicRootElement();
        OpenSubsonicExtensionsElement extensionsElement = new OpenSubsonicExtensionsElement();
        extensionsElement.setExtensions(OpenSubsonicExtensionUtils.getOpenSubsonicExtensions());
        try {
            rootElement.setStatus("ok");
            rootElement.addChildNode("openSubsonicExtensions",extensionsElement);
            return new ResponseEntity<>(mapper.writeValueAsString(rootElement), HttpStatusCode.valueOf(200));
        }catch (Exception e){
            rootElement.setStatus("failed");
            rootElement.addChildNode("error", SubsonicError.from(0));
            return new ResponseEntity<>(mapper.writeValueAsString(rootElement), HttpStatusCode.valueOf(200));
        }
    }

    // -- /rest/getTokenInfo --

    @GetMapping(value = {"/rest/tokenInfo","/rest/tokenInfo.view"},produces = "application/json")
    ResponseEntity<String> tokenInfoGet(@RequestParam Map<String,String> params){
        return getTokenInfo(params.get("apiKey"));
    }

    @PostMapping(value = {"/rest/tokenInfo","/rest/tokenInfo.view"},produces = "application/json")
    ResponseEntity<String> tokenInfoPost(@RequestParam Map<String,String> params){
        return getTokenInfo(params.get("apiKey"));
    }

    ResponseEntity<String> getTokenInfo(String apiKey){
        SubsonicRootElement rootElement = new SubsonicRootElement();
        try {
            Optional<APIToken> token = tokenService.getApiTokenByTokenName(apiKey);
            // No check for token.isPresent is needed, as if it was null, it wouldn't get through the ApiKeyFilter.
            rootElement.addChildNode("tokenInfo",new TokenInfoElement(token.get().getUsername()));
            rootElement.setStatus("ok");
            return new ResponseEntity<>(mapper.writeValueAsString(rootElement), HttpStatusCode.valueOf(200));
        }catch (Exception e){
            rootElement.setStatus("failed");
            rootElement.addChildNode("error", SubsonicError.from(0));
            return new ResponseEntity<>(mapper.writeValueAsString(rootElement), HttpStatusCode.valueOf(200));
        }
    }

}
