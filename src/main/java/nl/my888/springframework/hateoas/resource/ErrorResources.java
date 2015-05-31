package nl.my888.springframework.hateoas.resource;


/**
 * Resource for multiple errors. See <a href="https://github.com/blongden/vnd.error">vnd.error</a>.
 * 
 * @author ejl
 *
 */
public class ErrorResources extends EmbeddedErrorResourceSupport {
    
    public Integer getTotal() {
        return getEmbedded().size();
    }
}
