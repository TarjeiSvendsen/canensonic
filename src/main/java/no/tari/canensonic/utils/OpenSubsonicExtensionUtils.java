package no.tari.canensonic.utils;

import no.tari.canensonic.api.system.OpenSubsonicExtension;

import java.util.ArrayList;
import java.util.List;

public class OpenSubsonicExtensionUtils {

    /**
     * Gets all the extensions supported by the server.
     * @return a list of {@link OpenSubsonicExtension}.
     */
    public static List<OpenSubsonicExtension> getOpenSubsonicExtensions(){
        List<OpenSubsonicExtension> openSubsonicExtensions = new ArrayList<>(15);
        openSubsonicExtensions.addAll(getServerExtensions());
        return openSubsonicExtensions;
    }

    /**
     * Gets the default extensions supported by the server
     * @return a list of {@link OpenSubsonicExtension}.
     */
    public static List<OpenSubsonicExtension> getServerExtensions(){
        return List.of(
                new OpenSubsonicExtension("formPost",List.of(1)),
                new OpenSubsonicExtension("apiKeyAuthentication",List.of(1))
                );
    }
}
