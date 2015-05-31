package nl.my888.springframework.hateoas.resource;

import java.util.Objects;

import org.springframework.hateoas.core.EmbeddedWrapper;

/**
 * Wrapper for embedded objects with user specified relation type.
 */
final class RelEmbeddedWrapper implements EmbeddedWrapper {

    private final String rel;
    private final Object wrapped;

    RelEmbeddedWrapper(Object wrapped, String rel) {
        this.rel = rel;
        this.wrapped = wrapped;
    }

    @Override
    public String getRel() {
        return rel;
    }

    @Override
    public boolean hasRel(String relToMatch) {
        return Objects.equals(rel, relToMatch);
    }

    @Override
    public boolean isCollectionValue() {
        return false;
    }

    @Override
    public Object getValue() {
        return wrapped;
    }

    @Override
    public Class<?> getRelTargetType() {
        return null;
    }

}
