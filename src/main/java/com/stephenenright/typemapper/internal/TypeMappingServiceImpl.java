package com.stephenenright.typemapper.internal;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.stephenenright.typemapper.DefaultMapperConfiguration;
import com.stephenenright.typemapper.MapMapperConfiguration;
import com.stephenenright.typemapper.TypeMapperConfiguration;
import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.TypeMappingService;
import com.stephenenright.typemapper.TypeToken;
import com.stephenenright.typemapper.TypeTransformer;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.internal.common.error.Errors;
import com.stephenenright.typemapper.internal.conversion.TypeConverterRegistry;
import com.stephenenright.typemapper.internal.util.ObjectUtils;
import com.stephenenright.typemapper.internal.util.ProxyUtils;

public class TypeMappingServiceImpl implements TypeMappingService {

    private final List<TypeConverterRegistry> converterRegistryList;
    private final TypeMappingConversionStrategyRegistry conversionStrategyRegistry;

    public TypeMappingServiceImpl(List<TypeConverterRegistry> converterRegistryList,
            TypeMappingConversionStrategyRegistry conversionStrategyRegistry) {
        this.converterRegistryList = converterRegistryList;
        this.conversionStrategyRegistry = conversionStrategyRegistry;
    }

    @Override
    public <S, D> D map(S src, Class<D> destination) {
        return map(src, destination, null);
    }

    @Override
    public <S, D> D map(S src, Class<D> destination, DefaultMapperConfiguration configuration) {
        return mapInternal(src, null, destination, configuration, TypeMappingToStrategy.OBJECT);
    }

    @Override
    public <S, D extends Map<String, Object>> D mapToMap(S src) {
        return mapToMap(src, null);
    }

    @Override
    public <S, D extends Map<String, Object>> D mapToMap(S src, MapMapperConfiguration configuration) {
        return mapInternal(src, null, Map.class, configuration, TypeMappingToStrategy.MAP);
    }

    @Override
    public <S, D extends Map<String, Object>> List<D> mapToListOfMap(List<S> src) {
        return mapToListOfMap(src, null);
    }

    @Override
    public <S, D extends Map<String, Object>> List<D> mapToListOfMap(List<S> src,
            MapMapperConfiguration configuration) {
        return mapInternal(src, null, List.class, configuration, TypeMappingToStrategy.LIST_OF_MAP);
    }

    @Override
    public <S, D> D map(TypeMappingContext<S, D> context) {
        try {
            TypeMappingContextImpl<S, D> contextImpl = (TypeMappingContextImpl<S, D>) context;
            TypeMappingConversionStrategy conversionStrategy = conversionStrategyRegistry
                    .get(contextImpl.getTypeMappingToStrategy());
            D result =  conversionStrategy.map(contextImpl);
            
            if(!contextImpl.hasParentContext()) {
               TypeTransformer<S,D> postTransformer = contextImpl.getConfiguration().getPostTransformer();
            
               if(postTransformer!=null) {
                   return postTransformer.tranform(contextImpl.getSource(), result);
               }
            }
 
            return result;
            

        } catch (Throwable t) {
            throw Errors.createGenericMappingExceptionIfNecessary(t, context);
        }
    }

    private <D> D mapInternal(Object source, D destination, Type destinationType,
            TypeMapperConfiguration configuration, TypeMappingToStrategy mappingToStrategy) {
        if(source==null) {
            return null;
        }
        
        
        
        if (destination != null)
            destinationType = ProxyUtils.<D>unProxy(destination.getClass());
        return mapInternal(source, ProxyUtils.<Object>unProxy(source.getClass()), destination,
                TypeToken.<D>of(destinationType), configuration, mappingToStrategy);
    }

    private <S, D> D mapInternal(S source, Class<S> sourceType, D destination, TypeToken<D> destinationTypeToken,
            TypeMapperConfiguration configuration, TypeMappingToStrategy mappingToStrategy) {

        TypeMappingContextImpl<S, D> context = new TypeMappingContextImpl<S, D>(
                configuration == null ? createConfiguration(mappingToStrategy) : configuration, source, sourceType,
                destination, destinationTypeToken.getRawType(), destinationTypeToken.getType(), this,
                mappingToStrategy);

        return map(context);
    }
    
    
    @Override
    public <S, D> TypeConverter<S, D> getTypeConverter(TypeMappingContext<S, D> context) {
        return getTypeConverter(context.getSourceType(), context.getDestinationType());
    }

    @Override
    public <S, D> TypeConverter<S, D> getTypeConverter(Class<S> sourceType, Class<D> destinationType) {
        TypeConverter<S, D> foundConverter = null;

        for (TypeConverterRegistry registry : converterRegistryList) {
            foundConverter = registry.getConverter(sourceType, destinationType);

            if (foundConverter != null) {
                return foundConverter;
            }
        }

        return foundConverter;
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

    public <S, D> TypeConverter<S, D> getTypeConverterFromContext(TypeMappingContextImpl<S, D> context) {
        return context.getTypeConverter(context.getSourceType(), context.getDestinationType());
    }

    private TypeMapperConfiguration createConfiguration(TypeMappingToStrategy mappingToStrategy) {
        if (mappingToStrategy == TypeMappingToStrategy.OBJECT) {
            return DefaultMapperConfiguration.create();
        } else if (mappingToStrategy == TypeMappingToStrategy.MAP
                || mappingToStrategy == TypeMappingToStrategy.LIST_OF_MAP) {
            return MapMapperConfiguration.create();
        } else {
            throw new IllegalArgumentException("Unsupported mapping to strategy");
        }
    }
}
