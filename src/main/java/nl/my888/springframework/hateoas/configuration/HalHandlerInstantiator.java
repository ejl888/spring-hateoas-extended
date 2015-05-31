package nl.my888.springframework.hateoas.configuration;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.Jackson2HalModule.HalLinkListSerializer;
import org.springframework.hateoas.hal.Jackson2HalModule.HalResourcesSerializer;
import org.springframework.util.Assert;

/**
 * Een HandlerInstantiator die naast de standaard serializers ook nog een
 * {@link nl.ilent.springframework.hateoas.configuration.StrictHalResourcesSerializer} registreert.
 *
 * @author ejl
 */
public class HalHandlerInstantiator extends HandlerInstantiator {

    private final Map<Class<?>, Object> instanceMap = new HashMap<>();

    /**
     * Maak de instantiator aan.
     *
     * @param relProvider
     *            voor het bepalen van relaties.
     * @param curieProvider
     *            de te gebruiken curieProvider.
     */
    public HalHandlerInstantiator(RelProvider relProvider, CurieProvider curieProvider) {
        this(relProvider, curieProvider, true);
    }

    /**
     * Maak de instantiator aan.
     *
     * @param relProvider
     *            voor het bepalen van relaties.
     * @param curieProvider
     *            de te gebruiken curieProvider.
     * @param enforceEmbeddedCollections
     *            forceer dat embedded collecties altijd als JSON-collectie worden gegenereerd, ook als er maar 1 element in
     *            zit.
     */
    public HalHandlerInstantiator(RelProvider relProvider, CurieProvider curieProvider, boolean enforceEmbeddedCollections) {
        Assert.notNull(relProvider, "RelProvider must not be null!");

        this.instanceMap.put(HalResourcesSerializer.class, new HalResourcesSerializer(relProvider, curieProvider,
                enforceEmbeddedCollections));
        this.instanceMap.put(StrictHalResourcesSerializer.class, new StrictHalResourcesSerializer(relProvider, curieProvider));
        this.instanceMap.put(HalLinkListSerializer.class, new HalLinkListSerializer(curieProvider));
    }

    private Object findInstance(Class<?> type) {
        final Object instance = instanceMap.get(type);
        return instance != null ? instance : BeanUtils.instantiateClass(type);
    }

    @Override
    public JsonDeserializer<?> deserializerInstance(DeserializationConfig config, Annotated annotated, Class<?> deserClass) {
        return (JsonDeserializer<?>) findInstance(deserClass);
    }

    @Override
    public KeyDeserializer keyDeserializerInstance(DeserializationConfig config, Annotated annotated, Class<?> keyDeserClass) {
        return (KeyDeserializer) findInstance(keyDeserClass);
    }

    @Override
    public JsonSerializer<?> serializerInstance(SerializationConfig config, Annotated annotated, Class<?> serClass) {
        return (JsonSerializer<?>) findInstance(serClass);
    }

    @Override
    public TypeResolverBuilder<?> typeResolverBuilderInstance(MapperConfig<?> config, Annotated annotated,
            Class<?> builderClass) {
        return (TypeResolverBuilder<?>) findInstance(builderClass);
    }

    @Override
    public TypeIdResolver typeIdResolverInstance(MapperConfig<?> config, Annotated annotated, Class<?> resolverClass) {
        return (TypeIdResolver) findInstance(resolverClass);
    }
}
