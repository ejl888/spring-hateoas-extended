package nl.my888.springframework.hateoas.resource;


/**
 * Resource support for embeded errors.
 * 
 * @author ejl
 *
 */
public class EmbeddedErrorResourceSupport extends TypedEmbeddedResourceSupport<ErrorResource> {

    /**
     * embed the error.
     * @param error to embed.
     * @return {@code this} (fluent interface).
     */
    public EmbeddedErrorResourceSupport addError(ErrorResource error) {
        embed(error);
        return this;
    }
}
