package no.tari.canensonic.api.auth.token;

import no.tari.canensonic.api.base.responses.SubsonicElement;

public class TokenInfoElement implements SubsonicElement {
    private String username;


    public TokenInfoElement(){
    }

    public TokenInfoElement(String username){
        this.username = username;
    }

    public TokenInfoElement from(APIToken token){
        return new TokenInfoElement();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
