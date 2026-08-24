package no.tari.canensonic.api.system;

import com.fasterxml.jackson.annotation.JsonValue;
import no.tari.canensonic.api.base.responses.SubsonicElement;

import java.util.List;

public class OpenSubsonicExtensionsElement implements SubsonicElement {

    private List<OpenSubsonicExtension> extensions;

    @JsonValue
    public List<OpenSubsonicExtension> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<OpenSubsonicExtension> extensions) {
        this.extensions = extensions;
    }

}
