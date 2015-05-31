package nl.my888.springframework.hateoas.configuration;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.Jackson2HalModule;

/**
 * HAL Resource serializer die zich strict aan de HAL spec houdt. Bij 1 element dus geen lijst met 1 element maar direct het
 * element met het relatie type hiervoor.
 *
 * @author ejl
 *
 */
public class StrictHalResourcesSerializer extends Jackson2HalModule.HalResourcesSerializer {

    private final RelProvider relProvider;
    private final CurieProvider curieProvider;

    /**
     * Maak een instantie aan.
     *
     * @param relProvider nodig om relatie types te bepalen
     * @param curieProvider nodig om curies te bepalen.
     */
    public StrictHalResourcesSerializer(RelProvider relProvider, CurieProvider curieProvider) {
        super(relProvider, curieProvider, false);
        this.relProvider = relProvider;
        this.curieProvider = curieProvider;
    }

    /**
     * Maak een instantie aan.
     *
     * @param property die geserializeerd wordt.
     * @param relProvider nodig om relatie types te bepalen.
     * @param curieProvider nodig om curies te bepalen.
     */
    public StrictHalResourcesSerializer(BeanProperty property, RelProvider relProvider, CurieProvider curieProvider) {
        super(property, relProvider, curieProvider, false);
        this.relProvider = relProvider;
        this.curieProvider = curieProvider;
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        return new StrictHalResourcesSerializer(property, relProvider, curieProvider);
    }
}
