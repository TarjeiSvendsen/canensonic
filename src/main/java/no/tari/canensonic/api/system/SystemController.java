package no.tari.canensonic.api.system;

import no.tari.canensonic.api.base.responses.SubsonicError;
import no.tari.canensonic.api.base.responses.SubsonicRootElement;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
public class SystemController {

    private final ObjectMapper mapper;

    public SystemController(ObjectMapper mapper) {
        this.mapper = mapper;
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

}
