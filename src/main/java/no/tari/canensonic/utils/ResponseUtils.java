package no.tari.canensonic.utils;

import no.tari.canensonic.api.base.responses.SubsonicError;
import no.tari.canensonic.api.base.responses.SubsonicRootElement;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import tools.jackson.databind.ObjectMapper;

public class ResponseUtils {


    public static ResponseEntity<String> produceEmptySubsonicResponse(ObjectMapper mapper){
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
