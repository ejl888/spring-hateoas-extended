package nl.my888.springframework.hateoas.links;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpMethod;

/**
 * Link Builder with extended functionality.
 *
 */
public final class ExtendedLinkBuilder implements LinkBuilder {

    private LinkBuilder linkBuilder;
  
    private TemplateVariables templateVariables = new TemplateVariables();

    private final Map<String, String[]> parameters = new LinkedHashMap<>();

    private final Set<String> methods = new LinkedHashSet<>(5);

    private ExtendedLinkBuilder(LinkBuilder linkBuilder) {
        this.linkBuilder = linkBuilder;
    }

    /**
     * Constructor wrapping a standard Spring HATEAOS LinkBuilder.
     * @param linkBuilder linkbuilder wrapped.
     * @return extended link builder.
     */
    public static ExtendedLinkBuilder extendLinkBuilder(LinkBuilder linkBuilder) {
        return new ExtendedLinkBuilder(linkBuilder);
    }

    /**
     * Return an extended link builder which links to the given invocation value.
     *
     * @param invocationValue
     *            the invocation value
     * @return the extended link builder
     */
    public static ExtendedLinkBuilder linkTo(Object invocationValue) {
        return ExtendedLinkBuilder.extendLinkBuilder(ControllerLinkBuilder.linkTo(invocationValue));

    }

    /**
     * Put a specific query link parameter.
     * @param name parameter name.
     * @param values parameter values(s).
     * @return {@code this} to enable fluent interface.
     */
    public ExtendedLinkBuilder putParam(String name, String... values) {
        parameters.put(name, values);
        return this;
    }

    /**
     * Put multiple query link parameters.
     * @param additionalParameters all query parameters to add to this link.
     * @return {@code this} to enable fluent interface.
     */
    public ExtendedLinkBuilder putParams(Map<String, String[]> additionalParameters) {
        parameters.putAll(additionalParameters);
        return this;
    }

    @Override
    public ExtendedLinkBuilder slash(Object object) {
        linkBuilder = linkBuilder.slash(object);
        return this;
    }

    @Override
    public ExtendedLinkBuilder slash(Identifiable<?> identifiable) {
        linkBuilder = linkBuilder.slash(identifiable);
        return this;
    }

    @Override
    public URI toUri() {
        return appendParameters(linkBuilder.toUri());
    }

    @Override
    public Link withRel(String rel) {
        return new ExtendedLink(new UriTemplate(toUri().toString(), templateVariables), rel, methods);
    }

    @Override
    public Link withSelfRel() {
        return withRel(Link.REL_SELF);
    }

    private URI appendParameters(URI uri) {
        if (parameters.isEmpty()) {
            return uri;
        }

        try {
            final URIBuilder uriBuilder = new URIBuilder(uri);           

            for (Map.Entry<String, String[]> param : parameters.entrySet()) {                
                final String[] values = param.getValue();
                if (values != null) {
                    for (String value : values) {
                        uriBuilder.addParameter(param.getKey(), value);
                    }
                }
            }
            
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Add a template request variable to the Uri.
     * @param name template variabele name.
     * @return builder itself.
     */
    public ExtendedLinkBuilder addTemplateRequestVariable(String name) {
        templateVariables = templateVariables.concat(new TemplateVariable(name, VariableType.REQUEST_PARAM));
        return this;
    }

    /**
     * Set allowGet fluently.
     * 
     * @return builder itself.
     */
    public ExtendedLinkBuilder allowGet() {
        methods.add(HttpMethod.GET.name());
        return this;
    }

    /**
     * Set allowPut fluently.
     * 
     * @return builder itself.
     */
    public ExtendedLinkBuilder allowPut() {
        methods.add(HttpMethod.PUT.name());
        return this;
    }

    /**
     * Set allowPost fluently.
     * 
     * @return builder itself.
     */
    public ExtendedLinkBuilder allowPost() {
        methods.add(HttpMethod.POST.name());
        return this;
    }

    /**
     * Set allowDelete fluently.
     * 
     * @return builder itself.
     */
    public ExtendedLinkBuilder allowDelete() {
        methods.add(HttpMethod.DELETE.name());
        return this;
    }

    /**
     * Set allowPatch fluently.
     * 
     * @return builder itself.
     */
    public ExtendedLinkBuilder allowPatch() {
        methods.add(HttpMethod.PATCH.name());
        return this;
    }

}
