package com.stephenenright.typemapper.internal;

import java.lang.reflect.Type;

import javax.annotation.Nullable;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.TypeToken;
import com.stephenenright.typemapper.internal.util.ProxyUtils;

public class TypeMappingServiceImpl implements TypeMappingService {

    @Override
    public <S, D> D map(S src, Class<D> destination) {
        return mapInternal(src, null, destination);
    }
    
    @Override
    public <S, D> D map(TypeMappingContext<S, D> context) {
        D destination = null;
        return null;
    }

    
    private <D> D mapInternal(Object source, @Nullable D destination, Type destinationType) {
        if (destination != null)
            destinationType = ProxyUtils.<D>unProxy(destination.getClass());
        return mapInternal(source, ProxyUtils.<Object>unProxy(source.getClass()), destination,
                TypeToken.<D>of(destinationType));
    }

    private <S, D> D mapInternal(S source, Class<S> sourceType, D destination, TypeToken<D> destinationTypeToken) {
        
        TypeMappingContextImpl<S, D> context = new TypeMappingContextImpl<S,D>(source, sourceType,
                destination, destinationTypeToken.getRawType(), destinationTypeToken.getType());
        
        return map(context);
        
    }
}
