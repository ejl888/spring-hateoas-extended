package nl.my888.springframework.hateoas.resource;


import org.springframework.hateoas.hal.CurieProvider;

/**
 * Resource supporting generic _embedded entries with specific relation.
 *
 * @author ejl
 *
 */
public class EmbeddedResourceSupport extends TypedEmbeddedResourceSupport<Object> {

    private static CurieProvider curieProvider;

    // TODO XXX improve later
    public static void setCurieProvider(CurieProvider curieProvider) {
        EmbeddedResourceSupport.curieProvider = curieProvider;
    }

    /**
     *
     * @param toEmbed Object to embed.
     * @param rel relation type.
     * @return {@code this} (fluent interface).
     */
    public EmbeddedResourceSupport embed(Object toEmbed, String rel) {
        embed(new RelEmbeddedWrapper(toEmbed, toRel(rel)));
        return this;
    }

    private String toRel(String rel) {
        return curieProvider == null ? rel : curieProvider.getNamespacedRelFor(rel);
    }
}

