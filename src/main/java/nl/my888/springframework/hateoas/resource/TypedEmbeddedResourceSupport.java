package nl.my888.springframework.hateoas.resource;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.my888.springframework.hateoas.configuration.StrictHalResourcesSerializer;
import org.springframework.hateoas.ResourceSupport;

/**
 * Resource supporting _embedded entries.
 * @author ejl
 * @param <T> type embedded object.
 */
public class TypedEmbeddedResourceSupport<T> extends ResourceSupport {

    @XmlElement(name = "embedded")
    @JsonProperty("_embedded")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY, using = StrictHalResourcesSerializer.class)
    private final List<T> embedded = new LinkedList<>();

    public List<T> getEmbedded() {
        return embedded;
    }

    /**
     * Embed the object.
     * @param toEmbed object to embed.
     * @return this.
     */
    public TypedEmbeddedResourceSupport<T> embed(T toEmbed) {
        embedded.add(toEmbed);
        return this;
    }

}
