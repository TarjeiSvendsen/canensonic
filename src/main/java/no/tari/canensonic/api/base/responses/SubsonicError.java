package no.tari.canensonic.api.base.responses;

public class SubsonicError implements SubsonicElement{
    int code;
    String message;
    String helpUrl;

    public SubsonicError(){}

    public SubsonicError(int code,String message,String helpUrl){
        this.code = code;
        this.message = message;
        this.helpUrl = helpUrl;
    }

    /**
     * Creates a Subsonic Error from the requested code.
     * @param code the error code
     * @return a Subsonic Error Element.
     */
    public static SubsonicError from(int code){
        return switch (code) {
            case 10 -> new SubsonicError(10,"Required parameter is missing","");
            case 20 -> new SubsonicError(20, "Incompatible Subsonic REST protocol version. Client must upgrade.", "");
            case 30 -> new SubsonicError(30, "Incompatible Subsonic REST protocol version. Server must upgrade.", "");
            case 40 -> new SubsonicError(40,"Wrong username or password.","");
            case 41 -> new SubsonicError(41,"Token authentication not supported for LDAP users.","");
            case 42 -> new SubsonicError(42,"Provided authentication mechanism not supported.","");
            case 43 -> new SubsonicError(43,"Multiple conflicting authentication mechanisms provided.","");
            case 44 -> new SubsonicError(44,"Invalid API key.","");
            case 50 -> new SubsonicError(50,"User is not authorized for the given operation.","");
            // Choosing to omit error 60, as it will never be used due to this being free and open source software.
            case 70 -> new SubsonicError(70,"The requested data was not found.","");
            default -> new SubsonicError(0, "Generic error", "");
        };
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHelpUrl() {
        return helpUrl;
    }

    public void setHelpUrl(String helpUrl) {
        this.helpUrl = helpUrl;
    }
}
