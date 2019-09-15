package com.stephenenright.typemapper.internal;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Nullable;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.TypeToken;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.internal.conversion.TypeConverterRegistry;
import com.stephenenright.typemapper.internal.util.ProxyUtils;

public class TypeMappingServiceImpl implements TypeMappingService {

    private final List<TypeConverterRegistry> converterRegistryList;

    public TypeMappingServiceImpl(List<TypeConverterRegistry> converterRegistryList) {
        this.converterRegistryList = converterRegistryList;
    }

    @Override
    public <S, D> D map(S src, Class<D> destination) {
        return mapInternal(src, null, destination);
    }

    @Override
    public <S, D> D map(TypeMappingContext<S, D> context) {
        TypeConverter<S, D> converter = getTypeConverterForContext(context);

        if (converter != null) {
            return convertWithTypeConverter(context,converter);
        }

        return null;
    }

    private <D> D mapInternal(Object source, @Nullable D destination, Type destinationType) {
        if (destination != null)
            destinationType = ProxyUtils.<D>unProxy(destination.getClass());
        return mapInternal(source, ProxyUtils.<Object>unProxy(source.getClass()), destination,
                TypeToken.<D>of(destinationType));
    }

    private <S, D> D mapInternal(S source, Class<S> sourceType, D destination, TypeToken<D> destinationTypeToken) {

        TypeMappingContextImpl<S, D> context = new TypeMappingContextImpl<S, D>(source, sourceType, destination,
                destinationTypeToken.getRawType(), destinationTypeToken.getType(), this);

        return map(context);
    }

    private <S, D> D convertWithTypeConverter(TypeMappingContext<S, D> context, TypeConverter<S, D> converter) {
        if(converter==null) {
            return null;
        }
        

        try {
            return converter.convert(context);
        } catch (Exception e) {
            throw e;
        }
    }

    private <S, D> TypeConverter<S, D> getTypeConverterForContext(TypeMappingContext<S, D> context) {
        TypeConverter<S, D> foundConverter = null;

        for (TypeConverterRegistry registry : converterRegistryList) {
            foundConverter = registry.getConverter(context.getSourceType(), context.getDestinationType());

            if (foundConverter != null) {
                return foundConverter;
            }
        }

        return foundConverter;
    }

}
