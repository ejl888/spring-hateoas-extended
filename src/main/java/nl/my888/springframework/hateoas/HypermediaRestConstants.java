package nl.my888.springframework.hateoas;

import org.springframework.http.MediaType;

/**
 * Hypermedia Rest MediaType constanten. 
 *
 */
public final class HypermediaRestConstants {

    /** Content type "application/hal+json, see http://tools.ietf.org/html/draft-kelly-json-hal. */
    public static final MediaType MEDIA_TYPE_APPLICATION_HAL_JSON = new MediaType("application", "hal+json");
    public static final String APPLICATION_HAL_JSON = "application/hal+json";

    /** Content tyoe "application/vnd.error+json", see: https://github.com/blongden/vnd.error. */
    public static final MediaType MEDIA_TYPE_APPLICATION_VND_ERROR_JSON = new MediaType("application", "vnd.error+json");
    public static final String APPLICATION_VND_ERROR_JSON = "application/vnd.error+json";

    /** Content type "application/json. */
    public static final MediaType MEDIA_TYPE_APPLICATION_JSON = MediaType.APPLICATION_JSON;
    public static final String APPLICATION_JSON = "application/json";

    private HypermediaRestConstants() {
        // util class
    }
}
