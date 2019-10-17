package com.stephenenright.typemapper;

import java.lang.reflect.Type;

import com.stephenenright.typemapper.internal.type.mapping.TypeMapping;

public interface TypeMappingContext<S, D> {

    public S getSource();

    public Class<S> getSourceType();

    public D getDestination();

    public Class<D> getDestinationType();

    public Type getGenericDestinationType();

    public boolean hasDestination();

    public TypeMapping getMapping();

    public TypeMappingService getMappingService();

    public <CS, CD> TypeMappingContext<CS, CD> createChild(CS source, Class<CD> destinationType);

    public <CS, CD> TypeMappingContext<CS, CD> createChildForObject(CS source, CD destination);

    public TypeMapperConfiguration getConfiguration();
    
    
//    public static <S, D> TypeMappingContext<S, D> of(S src, Class<D> destination) {
//        return TypeMappingContext.of(src, null, destination);
//    }
//
//    public static <S, D> TypeMappingContext<S, D> of(Object source, @Nullable D destination, Type destinationType) {
//        if (destination != null) {
//            destinationType = ProxyUtils.<D>unProxy(destination.getClass());
//        }
//           
//        return TypeMappingContext.of(source, ProxyUtils.<Object>unProxy(source.getClass()), destination,
//                TypeToken.<D>of(destinationType));
//    }
//
//    public static <S, D> TypeMappingContext<S, D> of(S source, Class<S> sourceType, D destination,
//            TypeToken<D> destinationTypeToken) {
//
//        return TypeMappingContextImpl<S, D> context = new TypeMappingContextImpl<S, D>(TypeMapperConfiguration.create(),
//                source, sourceType, destination, destinationTypeToken.getRawType(), destinationTypeToken.getType(),
//                this);
//
//    }

}
