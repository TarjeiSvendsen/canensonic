package no.tari.canensonic.utils;

import jakarta.servlet.http.HttpServletResponse;
import no.tari.canensonic.api.base.responses.SubsonicError;
import no.tari.canensonic.api.base.responses.SubsonicRootElement;
import org.springframework.http.MediaType;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ErrorWriterUtils {

    /**
     * Writes a subsonic error element to a {@link java.io.PrintWriter}, in this case returned by {@link HttpServletResponse#getWriter()}
     * @param response the response to write to
     * @param mapper the object mapper to write with
     * @param errorCode the error code to write
     * @throws IOException in case any IO exception occurs during writing.
     */
    public static void writeErrorElementToWriter(HttpServletResponse response, ObjectMapper mapper,int errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        SubsonicRootElement responseElement = new SubsonicRootElement();
        responseElement.setStatus("failed");
        responseElement.addChildNode("error", SubsonicError.from(errorCode));
        mapper.writeValue(response.getWriter(),responseElement);
    }

}
