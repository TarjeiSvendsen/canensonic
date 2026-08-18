package no.tari.canensonic.api.base.responses;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonTypeName(value = "subsonic-response")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class SubsonicRootElement {
    private String status;
    private String version;
    private String type;
    private String serverVersion;
    private final boolean openSubsonic = true;
    @JsonAnyGetter
    private Map<String,SubsonicElement> childNodes;

    public SubsonicRootElement(){
        this.version = "1.16.1";
        this.type = "CanenSonic";
        this.serverVersion = "0.0.1";
        this.childNodes = new HashMap<>();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public boolean isOpenSubsonic() {
        return openSubsonic;
    }

    public Map<String, SubsonicElement> getChildNodes() {
        return childNodes;
    }

    public void addChildNode(String childNodeKey, SubsonicElement childNode) {
        this.childNodes.put(childNodeKey,childNode);
    }

    public void setChildNodes(Map<String, SubsonicElement> childNodes) {
        this.childNodes = childNodes;
    }
}
