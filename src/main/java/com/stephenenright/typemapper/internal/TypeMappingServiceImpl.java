package com.stephenenright.typemapper.internal;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Nullable;

import com.stephenenright.typemapper.TypeMapperConfiguration;
import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.TypeToken;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.exception.TypeMappingException;
import com.stephenenright.typemapper.internal.common.error.Errors;
import com.stephenenright.typemapper.internal.conversion.TypeConverterRegistry;
import com.stephenenright.typemapper.internal.type.info.TypeInfoRegistry;
import com.stephenenright.typemapper.internal.type.info.TypePropertyGetter;
import com.stephenenright.typemapper.internal.type.info.TypePropertyInfoRegistry;
import com.stephenenright.typemapper.internal.type.info.TypePropertySetter;
import com.stephenenright.typemapper.internal.type.mapping.TypeMapping;
import com.stephenenright.typemapper.internal.type.mapping.TypeMappingInfo;
import com.stephenenright.typemapper.internal.type.mapping.TypeMappingInfoRegistry;
import com.stephenenright.typemapper.internal.util.ArrayUtils;
import com.stephenenright.typemapper.internal.util.ClassUtils;
import com.stephenenright.typemapper.internal.util.CollectionUtils;
import com.stephenenright.typemapper.internal.util.ListUtils;
import com.stephenenright.typemapper.internal.util.ObjectUtils;
import com.stephenenright.typemapper.internal.util.ProxyUtils;

public class TypeMappingServiceImpl implements TypeMappingService {

    private final List<TypeConverterRegistry> converterRegistryList;
    private final TypeMappingInfoRegistry mappingInfoRegistry;
    private final TypeInfoRegistry typeInfoRegistry;
    private final TypePropertyInfoRegistry typePropertyInfoRegistry;

    public TypeMappingServiceImpl(List<TypeConverterRegistry> converterRegistryList,
            TypeMappingInfoRegistry mappingInfoRegistry, TypeInfoRegistry typeInfoRegistry,
            TypePropertyInfoRegistry typePropertyInfoRegistry) {
        this.converterRegistryList = converterRegistryList;
        this.mappingInfoRegistry = mappingInfoRegistry;
        this.typePropertyInfoRegistry = typePropertyInfoRegistry;
        this.typeInfoRegistry = typeInfoRegistry;
    }

    @Override
    public <S, D> D map(S src, Class<D> destination) {
        return map(src,destination,null);
    }
    
    @Override
    public <S, D> D map(S src, Class<D> destination, TypeMapperConfiguration configuration) {
        return mapInternal(src, null, destination, configuration);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S, D> D map(TypeMappingContext<S, D> context) {
        try {
            TypeMappingContextImpl<S, D> contextImpl = (TypeMappingContextImpl<S, D>) context;
            Class<D> destinationType = context.getDestinationType();
            D destinationObj = null;

            if (!CollectionUtils.isCollection(destinationType) && !ArrayUtils.isArray(destinationType)) {
                D potentialCircularDest = contextImpl.destinationForSource();
                if (potentialCircularDest != null
                        && potentialCircularDest.getClass().isAssignableFrom(contextImpl.getDestinationType())) {
                    return potentialCircularDest;
                }
            }

            TypeMappingInfo<S, D> mappingInfo = mappingInfoRegistry.get(context.getSourceType(),
                    context.getDestinationType());

            if (mappingInfo != null) {
                destinationObj = mapWithInfo(contextImpl, mappingInfo);
            } else {
                TypeConverter<S, D> converter = getTypeConverterFromContext(contextImpl);

                if (converter != null) {
                    destinationObj = convertWithTypeConverter(context, converter);
                } else if (converter == null
                        && ClassUtils.isNotPrimitive(context.getSourceType(), context.getDestinationType())) {
                    mappingInfo = mappingInfoRegistry.getOrRegister(context.getSource(), context.getSourceType(),
                            context.getDestinationType(), this, contextImpl);
                    destinationObj = mapWithInfo(contextImpl, mappingInfo);
                } else if (context.getDestinationType().isAssignableFrom(context.getSourceType())) {
                    destinationObj = (D) context.getSource();
                }
            }

            contextImpl.setDestination(destinationObj, true);
            return destinationObj;
        } catch (Throwable t) {
            throw Errors.createGenericMappingExceptionIfNecessary(t, context);
        }
    }

    private <D> D mapInternal(Object source, @Nullable D destination, Type destinationType, 
            TypeMapperConfiguration configuration) {
        if (destination != null)
            destinationType = ProxyUtils.<D>unProxy(destination.getClass());
        return mapInternal(source, ProxyUtils.<Object>unProxy(source.getClass()), destination,
                TypeToken.<D>of(destinationType),configuration);
    }

    private <S, D> D mapInternal(S source, Class<S> sourceType, D destination, 
            TypeToken<D> destinationTypeToken,TypeMapperConfiguration configuration) {

        TypeMappingContextImpl<S, D> context = new TypeMappingContextImpl<S, D>(
                configuration==null ? TypeMapperConfiguration.create() : configuration,
                source, sourceType, destination, destinationTypeToken.getRawType(), destinationTypeToken.getType(),
                this);

        return map(context);
    }
    
    @Override
    public <S, D> TypeConverter<S, D> getTypeConverter(TypeMappingContext<S, D> context) {
        return getTypeConverter(context.getSourceType(), context.getDestinationType());
    }
    
    @Override
    public <S,D> TypeConverter<S,D> getTypeConverter(Class<S> sourceType, Class<D> destinationType) {
        TypeConverter<S, D> foundConverter = null;

        for (TypeConverterRegistry registry : converterRegistryList) {
            foundConverter = registry.getConverter(sourceType, destinationType);

            if (foundConverter != null) {
                return foundConverter;
            }
        }

        return foundConverter;
    }
    
    
    private <S, D> D convertWithTypeConverter(TypeMappingContext<S, D> context, TypeConverter<S, D> converter) {
        if (converter == null) {
            return null;
        }

        try {
            return converter.convert(context);
        } catch (TypeMappingException e) {
            throw e;
        } catch (Throwable t) {
            throw new Errors().errorTypeConversion(context.getSourceType(), context.getDestinationType(), converter, t)
                    .toMappingException();
        }
    }


    private <S, D> D mapWithInfo(TypeMappingContext<S, D> context, TypeMappingInfo<S, D> typeMap) {

        if (typeMap.getConverter() != null) {
            return convertWithTypeConverter(context, typeMap.getConverter());
        }

        if (context.getDestination() == null) {
            D destination = createDestination(context);
            if (destination == null) {
                return null;
            }
        }

        for (TypeMapping mapping : typeMap.getTypeMappings()) {
            mapWithMapping(mapping, (TypeMappingContextImpl<S, D>) context);
        }

        return context.getDestination();
    }

    private <S, D> void mapWithMapping(TypeMapping mapping, TypeMappingContextImpl<S, D> context) {
        String destinationPath = mapping.getDestinationPath();

        if (context.isPathProcessed(destinationPath)) {
            return;
        }

        Object sourceObject = resolveSourceValue(mapping, context);
        TypeMappingContextImpl<Object, Object> propertyContext = createContextForProperty(sourceObject, mapping,
                context);
        setDestinationValue(mapping, propertyContext, context);
    }

    @Override
    public <S, D> D createDestination(TypeMappingContext<S, D> context) {
        D destination = createDestination(context.getDestinationType());
        ((TypeMappingContextImpl<S, D>) context).setDestination(destination, true);
        return destination;
    }

    public <D> D createDestination(Class<D> type) {
        try {
            D destination = ObjectUtils.newInstance(type);
            return destination;
        } catch (Exception e) {
            throw new Errors().errorCreatingDestination(type, e).toMappingException();
        }
    }

    private Object resolveSourceValue(TypeMapping mapping, TypeMappingContext<?, ?> context) {
        Object source = context.getSource();

        for (TypePropertyGetter accessor : mapping.getSourceGetters()) {
            source = accessor.getValue(source);
        }

        return source;
    }

    @SuppressWarnings("unchecked")
    private <S, D> void setDestinationValue(TypeMapping mapping, TypeMappingContextImpl<Object, Object> propertyContext,
            TypeMappingContextImpl<S, D> context) {

        String destinationPath = context.getDestinationPath() + mapping.getDestinationPath();

        TypeConverter<Object, Object> converter = (TypeConverter<Object, Object>) mapping.getConverter();

        if (converter != null) {
            context.setPathProcessed(destinationPath);
        }

        Object destination = propertyContext.resolveParentDestination(typeInfoRegistry);

        if (destination == null) {
            return;
        }

        // now we have parent destination object resolved set the actual value
        TypePropertySetter setter = ListUtils.getLastElement(mapping.getDestinationSetters());
        TypePropertyGetter getter = typePropertyInfoRegistry.getterFor(setter.getTypeInDeclaringClass(),
                setter.getName(), context.getConfiguration());

        Object destinationValue = null;
        if (propertyContext.isProvidedDestination() && getter != null) {
            destinationValue = getter.getValue(destination);
            propertyContext.setDestination(destinationValue, true);
        }

        if (converter != null) {
            destinationValue = convertWithTypeConverter(propertyContext, converter);
        } else if (propertyContext.getSource() != null) {
            destinationValue = map(propertyContext);
        } else {
            converter = getTypeConverterFromContext(propertyContext);
            if (converter != null) {
                destinationValue = convertWithTypeConverter(propertyContext, converter);
            }
        }

        context.addDestinationToCache(destinationPath, destinationValue);

        if (destinationValue != null) {
            setter.setValue(destination,
                    destinationValue == null ? ClassUtils.getPrimitiveDefaultValue(setter.getType())
                            : destinationValue);

        }

        if (destinationValue == null) {
            context.setPathProcessed(propertyContext.getDestinationPath());
        }

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private TypeMappingContextImpl<Object, Object> createContextForProperty(Object source, TypeMapping mapping,
            TypeMappingContextImpl<?, ?> context) {

        Class<?> sourceType = mapping.getSourceType();

        if (source != null) {
            sourceType = ProxyUtils.unProxy(source.getClass());
        }

        Class<Object> destinationType = (Class<Object>) mapping.getDesintationType();
        Type genericDestinationType = context.genericDestinationPropertyType(mapping.getDesintationGenericType());
        return new TypeMappingContextImpl(context, source, sourceType, null, destinationType, genericDestinationType,
                mapping, true);
    }
    
    
    public <S, D> TypeConverter<S, D> getTypeConverterFromContext(TypeMappingContextImpl<S, D> context) {
        return context.getTypeConverter(context.getSourceType(), context.getDestinationType());
    }
    

}
