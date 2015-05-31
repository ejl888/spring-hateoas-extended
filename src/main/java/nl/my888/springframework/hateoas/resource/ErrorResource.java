package nl.my888.springframework.hateoas.resource;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import nl.my888.springframework.hateoas.links.ProfileLink;
import nl.my888.springframework.hateoas.RelationTypes;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.core.Relation;

/**
 * Error Resource, see <a href="https://github.com/blongden/vnd.error">vnd.error</a>.
 * 
 * @author ejl
 *
 */
@Relation(value = RelationTypes.ERROR, collectionRelation = RelationTypes.ERRORS)
public final class ErrorResource extends EmbeddedErrorResourceSupport {

    public static final URI ERROR_PROFILE_URI = URI.create("http://nocarrier.co.uk/profiles/vnd.error/");

    private static final String ABOUT_REL = "about";
    
    private final String message;
    private final String path;
    private final String logref;

    /**
     * Builder for {@link ErrorResource}.
     *
     */
    public static final class Builder {
        
        private String message;
        private String path;
        private String logref;
        private Link aboutLink;
        
        private final List<ErrorResource> errorResources = new LinkedList<>();
        
        private Builder() {
            // use factory method only.
        }

        public Builder withMessage(String messageValue) {
            this.message = messageValue;
            return this;
        }
        
        public Builder withLogref(String logrefValue) {
            this.logref = logrefValue;
            return this;
        }

        public Builder withPath(String pathValue) {
            this.path = pathValue;
            return this;
        }

        public Builder about(Link link) {
            this.aboutLink = link;
            return this;
        }

        public Builder addError(ErrorResource errorResource) {
            errorResources.add(errorResource);
            return this;
        }

        public ErrorResource build() {
            return new ErrorResource(this);
        }
    }

    private ErrorResource(Builder builder) {
        super();
        this.message = builder.message;
        this.path = builder.path;
        this.logref = builder.logref;

        if (builder.aboutLink != null) {
            add(builder.aboutLink.withRel(ABOUT_REL));
        }
        
        for (ErrorResource errorResource : builder.errorResources) {
            addError(errorResource);
        }

        add(new ProfileLink(ErrorResource.ERROR_PROFILE_URI));
    }

    /**
     * Factory method to start building an {@link ErrorResource}.
     * @return the builder.
     */
    public static Builder errorResourceBuilder() {
        return new Builder();
    }

    public String getMessage() {
        return message;
    }
    
    public String getPath() {
        return path;
    }
    
    public String getLogref() {
        return logref;
    }
}
