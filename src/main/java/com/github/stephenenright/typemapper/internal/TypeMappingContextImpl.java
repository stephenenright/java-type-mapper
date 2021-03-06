package com.github.stephenenright.typemapper.internal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.github.stephenenright.typemapper.converter.TypeConverter;
import com.github.stephenenright.typemapper.internal.common.CommonConstants;
import com.github.stephenenright.typemapper.internal.type.mapping.TypeMapping;
import com.github.stephenenright.typemapper.TypeInfoRegistry;
import com.github.stephenenright.typemapper.TypeMapperConfiguration;
import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.TypeMappingService;
import com.github.stephenenright.typemapper.TypeToken;
import com.github.stephenenright.typemapper.internal.type.info.TypePropertyGetter;
import com.github.stephenenright.typemapper.internal.type.info.TypePropertySetter;
import com.github.stephenenright.typemapper.internal.util.AssertUtils;
import com.github.stephenenright.typemapper.internal.util.ClassUtils;
import com.github.stephenenright.typemapper.internal.util.FunctionalUtils;
import com.github.stephenenright.typemapper.internal.util.ObjectUtils;
import com.github.stephenenright.typemapper.internal.util.PropertyPathUtils;
import com.github.stephenenright.typemapper.internal.util.ProxyUtils;

public class TypeMappingContextImpl<S, D> implements TypeMappingContext<S, D> {

    private final Map<String, Object> destinationCache;
    private Object destinationRoot;
    private final Object sourceRoot;
    private final S source;
    private final Class<S> sourceType;
    private final Class<D> destinationType;
    private D destination;
    private final Map<Object, Object> sourceToDestination;
    private final Type genericDestinationType;
    private final TypeMappingService mappingService;
    private TypeMapping mapping;
    private final TypeMapperConfiguration configuration;
    private final List<String> processedPaths;
    private String sourcePath;
    private final String destinationPath;
    private TypeMappingContextImpl<?, ?> parent;
    private boolean providedDestination;
    private final TypeMappingToStrategy strategy;

    public TypeMappingContextImpl(TypeMapperConfiguration configuration, S source, Class<D> destinationType,
            TypeMappingService mappingService, TypeMappingToStrategy strategy) {
        this(configuration, source, ProxyUtils.unProxy(source.getClass()), null, destinationType,
                TypeToken.<D>of(destinationType).getType(), mappingService, strategy);
    }

    public TypeMappingContextImpl(TypeMapperConfiguration configuration, S source, Class<S> sourceType, D destination,
            Class<D> destinationType, Type genericDestinationType, TypeMappingService mappingService,
            TypeMappingToStrategy strategy) {
        AssertUtils.notNull(configuration, "Configuration cannot be null");
        this.sourceRoot = source;
        this.source = source;
        this.sourceType = sourceType;
        this.strategy = strategy;
        this.parent = null;
        this.sourcePath = CommonConstants.EMPTY_STRING;
        this.destinationPath = CommonConstants.EMPTY_STRING;
        this.configuration = configuration;
        this.destination = destination;
        this.destinationRoot = destination;
        providedDestination = destination != null;
        this.destinationType = destinationType;
        this.genericDestinationType = genericDestinationType;
        this.mappingService = mappingService;
        this.processedPaths = new LinkedList<String>();
        this.destinationCache = new HashMap<String, Object>();
        this.sourceToDestination = new IdentityHashMap<Object, Object>();

    }

    public TypeMappingContextImpl(TypeMappingContext<?, ?> context, S source, Class<S> sourceType, D destination,
            Class<D> destinationType, Type genericDestinationType) {
        this(context, source, sourceType, destination, destinationType, genericDestinationType, null, false);
    }

    /**
     * Creates a new <code>TypeMappingContext</code>
     * 
     * @param context
     * @param source
     * @param sourceType
     * @param destination
     * @param destinationType
     * @param genericDestinationType
     * @param mapping
     * @param inherit                indicates if certain parent values should be
     *                               inherited
     */
    public TypeMappingContextImpl(TypeMappingContext<?, ?> context, S source, Class<S> sourceType, D destination,
            Class<D> destinationType, Type genericDestinationType, TypeMapping mapping, boolean inherit) {
        TypeMappingContextImpl<?, ?> contextImpl = (TypeMappingContextImpl<?, ?>) context;

        this.parent = contextImpl;
        this.sourceRoot = contextImpl.getSourceRoot();
        this.source = source;
        this.sourceType = sourceType;
        this.strategy = parent.getTypeMappingToStrategy();
        this.configuration = context.getConfiguration();
        this.destination = destination;
        this.providedDestination = contextImpl.providedDestination;
        this.destinationType = destinationType;
        this.genericDestinationType = genericDestinationType;
        this.mappingService = context.getMappingService();
        this.mapping = mapping;
        this.sourcePath = mapping == null ? contextImpl.sourcePath
                : PropertyPathUtils.joinPaths(contextImpl.sourcePath, mapping.getSourcePath());
        this.destinationPath = mapping == null ? contextImpl.destinationPath
                : PropertyPathUtils.joinPaths(contextImpl.destinationPath, mapping.getDestinationPath());

        this.processedPaths = inherit ? contextImpl.processedPaths : new LinkedList<String>();
        this.destinationCache = inherit ? contextImpl.destinationCache : new HashMap<String, Object>();
        sourceToDestination = contextImpl.sourceToDestination;

    }

    @Override
    public S getSource() {
        return source;
    }

    public Object getSourceRoot() {
        return sourceRoot;
    }

    @Override
    public Class<S> getSourceType() {
        return sourceType;
    }

    @Override
    public D getDestination() {
        return destination;
    }

    public Object getDestinationRoot() {
        return destinationRoot;
    }

    public void setDestinationRoot(Object destination) {
        this.destinationRoot = destination;
    }

    @Override
    public Class<D> getDestinationType() {
        return destinationType;
    }

    @Override
    public Type getGenericDestinationType() {
        return genericDestinationType;
    }

    @Override
    public boolean hasDestination() {
        return destination != null;
    }

    public TypeMappingService getMappingService() {
        return mappingService;
    }

    public String getSourcePath() {
        return this.sourcePath;
    }

    public String getDestinationPath() {
        return this.destinationPath;
    }

    @SuppressWarnings("unchecked")
    public D destinationForSource() {
        return (D) sourceToDestination.get(source);
    }

    public void setDestination(D destination, boolean trackSource) {
        this.destination = destination;

        if (trackSource && !ClassUtils.isPrimitiveWrapper(sourceType)) {
            sourceToDestination.put(source, destination);
        }
    }

    @Override
    public TypeMapping getMapping() {
        return mapping;
    }

    @Override
    public TypeMapperConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public <CS, CD> TypeMappingContext<CS, CD> createChild(CS source, Class<CD> destinationType) {
        AssertUtils.notNull(source, "source");
        AssertUtils.notNull(destinationType, "destinationType");

        return new TypeMappingContextImpl<CS, CD>(this, source, ProxyUtils.unProxy(source.getClass()), null,
                destinationType, null);

    }

    @Override
    public <CS, CD> TypeMappingContext<CS, CD> createChildForObject(CS source, CD destination) {
        AssertUtils.notNull(source, "source");
        AssertUtils.notNull(destination, "destination");

        return new TypeMappingContextImpl<CS, CD>(this, source, ProxyUtils.unProxy(source.getClass()), destination,
                ProxyUtils.unProxy(source.getClass()), null);
    }

    public Type genericDestinationPropertyType(Type type) {
        if (type == null || !(type instanceof ParameterizedType) || genericDestinationType == null
                || destinationType.getTypeParameters().length == 0)
            return null;

        ParameterizedType parameterizedType = (ParameterizedType) type;
        if (parameterizedType.getActualTypeArguments().length == 0)
            return null;

        if (destinationType.getTypeParameters()[0] == parameterizedType.getActualTypeArguments()[0])
            return genericDestinationType;
        return null;
    }

    public boolean isPathProcessed(String subPath) {
        for (String path : this.processedPaths) {
            if (subPath.startsWith(path)) {
                return true;
            }

        }
        return false;
    }

    public void setPathProcessed(String path) {
        processedPaths.add(path);
    }

    /**
     * Resolve the parent destination object that we may wish to set the value on
     * 
     * @param <S>
     * @param <D>
     * @return the destination value of the parent
     */
    @SuppressWarnings("unchecked")
    public <S, D> Object resolveParentDestination(TypeInfoRegistry typeInfoRegistry) {
        List<TypePropertySetter> setterList = mapping.getDestinationSetters();
        StringBuilder destPathBuilder = new StringBuilder().append(parent.destinationPath);

        Object currentValue = parent.destination;

        int i = 0;
        int setterListLast = setterList.size() - 1;
        for (TypePropertySetter setter : setterList) {
            if (i >= setterListLast) {
                break;
            }

            if (currentValue == null) {
                break;
            }

            PropertyPathUtils.addPropertyPath(destPathBuilder, setter.getName());
            String destPath = destPathBuilder.toString();

            Object next = ObjectUtils.firstNotNull(FunctionalUtils.supplierFor(parent.destinationCache.get(destPath)),
                    parent.resolveDestinationValueByName(currentValue, setter.getName(), typeInfoRegistry));

            if (next == null && source != null) {
                // TODO consider making it configurable
                next = mappingService.createDestination(setter.getType());

            }

            if (next != null) {
                setter.setValue(currentValue, next);
                parent.destinationCache.put(destPath, next);
            }

            currentValue = next;
            i++;

        }

        return currentValue;
    }

    private Supplier<Object> resolveDestinationValueByName(final Object current, final String name,
            TypeInfoRegistry infoRegistry) {

        return new Supplier<Object>() {
            @Override
            public Object get() {
                if (providedDestination) {
                    TypePropertyGetter accessor = infoRegistry.get(current.getClass(), configuration)
                            .getPropertyGetters().get(name);
                    if (accessor != null)
                        return accessor.getValue(current);
                }
                return null;
            }

        };
    }

    @SuppressWarnings("hiding")
    public <S, D> TypeConverter<S, D> getTypeConverter(Class<S> sourceType, Class<D> destinationType) {
        TypeConverter<S, D> foundConverter = configuration.getTypeConverter(sourceType, destinationType);

        if (foundConverter != null) {
            return foundConverter;
        }

        return mappingService.getTypeConverter(sourceType, destinationType);
    }

    public boolean isProvidedDestination() {
        return providedDestination;
    }

    public void addDestinationToCache(String path, Object destination) {
        destinationCache.put(path, destination);
    }

    public TypeMappingToStrategy getTypeMappingToStrategy() {
        return strategy;
    }

    public boolean hasParentContext() {
        return parent != null;
    }
}
