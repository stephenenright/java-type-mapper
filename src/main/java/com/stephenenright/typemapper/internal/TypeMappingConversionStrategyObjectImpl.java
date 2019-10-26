package com.stephenenright.typemapper.internal;

import java.lang.reflect.Type;

import com.stephenenright.typemapper.TypeInfoRegistry;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.exception.TypeMappingException;
import com.stephenenright.typemapper.internal.common.error.Errors;
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
import com.stephenenright.typemapper.internal.util.PropertyPathUtils;
import com.stephenenright.typemapper.internal.util.ProxyUtils;

/**
 * Standard conversion strategy that converts to an object
 */
public class TypeMappingConversionStrategyObjectImpl extends TypeMappingConversionStrategyBase
        implements TypeMappingConversionStrategy {

    private final TypeMappingInfoRegistry mappingInfoRegistry;
    private final TypeInfoRegistry typeInfoRegistry;
    private final TypePropertyInfoRegistry typePropertyInfoRegistry;

    public TypeMappingConversionStrategyObjectImpl(TypeMappingInfoRegistry mappingInfoRegistry,
            TypeInfoRegistry typeInfoRegistry, TypePropertyInfoRegistry typePropertyInfoRegistry) {
        this.mappingInfoRegistry = mappingInfoRegistry;
        this.typeInfoRegistry = typeInfoRegistry;
        this.typePropertyInfoRegistry = typePropertyInfoRegistry;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S, D> D map(TypeMappingContextImpl<S, D> context) {
        final String sourcePath = context.getSourcePath();

        if (!processPathForProperty(PropertyPathUtils.getParentPath(sourcePath), sourcePath, context.getSource(),
                context)) {
            return null;
        }

        try {
            Class<D> destinationType = context.getDestinationType();
            D destinationObj = null;

            if (!CollectionUtils.isCollection(destinationType) && !ArrayUtils.isArray(destinationType)) {
                D potentialCircularDest = context.destinationForSource();
                if (potentialCircularDest != null
                        && potentialCircularDest.getClass().isAssignableFrom(context.getDestinationType())) {

                    return potentialCircularDest;
                }
            }

            TypeMappingInfo<S, D> mappingInfo = mappingInfoRegistry.get(context.getSourceType(),
                    context.getDestinationType());

            if (mappingInfo != null) {
                destinationObj = mapWithInfo(context, mappingInfo);
            } else {
                TypeConverter<S, D> converter = getTypeConverterFromContext(context);

                if (converter != null) {
                    destinationObj = convertWithTypeConverter(context, converter);
                } else if (converter == null
                        && ClassUtils.isNotPrimitive(context.getSourceType(), context.getDestinationType())) {
                    mappingInfo = mappingInfoRegistry.getOrRegister(context.getSource(), context.getSourceType(),
                            context.getDestinationType(), context.getMappingService(), context);
                    destinationObj = mapWithInfo(context, mappingInfo);
                } else if (context.getDestinationType().isAssignableFrom(context.getSourceType())) {
                    destinationObj = (D) context.getSource();
                }
            }

            context.setDestination(destinationObj, true);
            return destinationObj;
        } catch (Throwable t) {
            throw Errors.createGenericMappingExceptionIfNecessary(t, context);
        }
    }

    private <S, D> D mapWithInfo(TypeMappingContextImpl<S, D> context, TypeMappingInfo<S, D> typeMap) {

        if (typeMap.getConverter() != null) {
            return convertWithTypeConverter(context, typeMap.getConverter());
        }

        if (context.getDestination() == null) {
            D destination = context.getMappingService().createDestination(context);
            if (destination == null) {
                return null;
            }
        }

        for (TypeMapping mapping : typeMap.getTypeMappings()) {
            mapWithMapping(mapping, context);
        }

        return context.getDestination();
    }

    private <S, D> void mapWithMapping(TypeMapping mapping, TypeMappingContextImpl<S, D> context) {

        String destinationPath = mapping.getDestinationPath();

        if (context.isPathProcessed(destinationPath)) {
            return;
        }

        String propertyPath = PropertyPathUtils.joinPaths(context.getDestinationPath(), destinationPath);

        final String sourcePath = context.getSourcePath();
        final String sourcePropertyPath = PropertyPathUtils.joinPaths(context.getSourcePath(), mapping.getSourcePath());

        Object sourceObject = resolveSourceValue(mapping, context);

        if (!processPathForProperty(sourcePath, sourcePropertyPath, sourceObject, context)) {
            context.setPathProcessed(propertyPath);
            return;
        }

        TypeMappingContextImpl<Object, Object> propertyContext = createContextForProperty(sourceObject, mapping,
                context);
        setDestinationValue(mapping, propertyContext, context);
    }

    private <S, D> D convertWithTypeConverter(TypeMappingContextImpl<S, D> context, TypeConverter<S, D> converter) {
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

    private boolean processPathForProperty(String sourceParentPath, String sourcePath, Object sourceValue,
            TypeMappingContextImpl<?, ?> context) {
        if (sourceValue == null) {
            return false;
        }

        return context.getConfiguration().isMappingIncluded(sourceParentPath, sourcePath, sourceValue,
                typeInfoRegistry);
    }

}
