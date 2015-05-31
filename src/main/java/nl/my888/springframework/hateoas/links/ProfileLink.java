package nl.my888.springframework.hateoas.links;

import java.net.URI;

import nl.my888.springframework.hateoas.RelationTypes;
import org.springframework.hateoas.Link;

/**
 * Link for the 'profile' link relation type, see RFC RFC 6906:
 * https://tools.ietf.org/html/draft-wilde-profile-link-04.
 */
@SuppressWarnings("serial")
public final class ProfileLink extends Link {

    /**
     * Constructor.
     * @param href link.
     */
    public ProfileLink(String href) {
        super(href, RelationTypes.PROFILE);
    }

    /**
     * Constructor.
     * @param href van de link.
     */
    public ProfileLink(URI href) {
        this(href.toString());
    }

}
