package nl.my888.springframework.hateoas.links;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;

/**
 * A link extended with allowed HTTP methods.
 * 
 * @author ejl
 *
 */
@JsonIgnoreProperties(value = { "rel" })
public final class ExtendedLink extends Link {

    private static final long serialVersionUID = 465184861038994286L;

    @XmlAttribute
    private final List<String> allow = new ArrayList<>(5);

    /**
     * Bouw link op met href, zonder methodes.
     * @param link HAL link
     */
    public ExtendedLink(Link link) {
        this(link.getHref(), link.getRel(), null);
    }

    /**
     * Bouw link op met href.
     * @param href http referentiee
     * @param rel Ilent relatie type
     * @param methods methodes
     */
    ExtendedLink(String href, String rel, Collection<String> methods) {
        super(href, rel);
        if (methods != null) {
            this.allow.addAll(methods);
        }
    }

    /**
     * Bouw link op met href met UriTemplate.
     * @param uriTemplate template voor gebruik van templated variabelen.
     * @param rel Ilent relatie type
     * @param methods methodes
     */
    ExtendedLink(UriTemplate uriTemplate, String rel, Collection<String> methods) {
        super(uriTemplate, rel);
        if (methods != null) {
            this.allow.addAll(methods);
        }
    }

    @Override
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = Jackson2HalModule.TrueOnlyBooleanSerializer.class)
    public boolean isTemplated() {
        return super.isTemplated();
    }

    public List<String> getAllow() {
        return allow;
    }

    /**
     * Voeg DELETE toe aan toegestaane methodes.
     *
     * @return kopie van link met toegevoegde methode.
     */
    public ExtendedLink allowDelete() {
        return allowMethod(HttpMethod.DELETE.name());
    }

    /**
     * Voeg POST toe aan toegestaane methodes.
     *
     * @return kopie van link met toegevoegde methode.
     */
    public ExtendedLink allowPost() {
        return allowMethod(HttpMethod.POST.name());
    }

    /**
     * Voeg PATCH toe aan toegestaane methodes.
     *
     * @return kopie van link met toegevoegde methode.
     */
    public ExtendedLink allowPatch() {
        return allowMethod(HttpMethod.PATCH.name());
    }

    /**
     * Voeg GET toe aan toegestaane methodes.
     *
     * @return kopie van link met toegevoegde methode.
     */
    public ExtendedLink allowGet() {
        return allowMethod(HttpMethod.GET.name());
    }

    /**
     * Voeg PUT toe aan toegestaane methodes.
     *
     * @return kopie van link met toegevoegde methode.
     */
    public ExtendedLink allowPut() {
        return allowMethod(HttpMethod.PUT.name());
    }

    private ExtendedLink allowMethod(String name) {
        final Collection<String> methods = new ArrayList<>(allow);
        methods.add(name);
        return new ExtendedLink(getHref(), getRel(), methods);
    }
}
