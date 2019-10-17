package com.stephenenright.typemapper;

import java.util.Map;

import com.stephenenright.typemapper.converter.TypeConverter;

public interface TypeMappingService {
    
    public <S, D> D map(S src, Class<D> destination);

    public <S, D> D map(S src, Class<D> destination, TypeMapperConfiguration configuration);
    
    public <S, D extends Map<String,Object>> Map<String,Object> mapToMap(S src);

    public <S, D extends Map<String,Object>> Map<String,Object> mapToMap(S src, TypeMapperConfiguration configuration);
    
    public <S, D> D map(TypeMappingContext<S, D> context);

    public <S, D> D createDestination(TypeMappingContext<S, D> context);

    public <D> D createDestination(Class<D> type);

    public <S, D> TypeConverter<S, D> getTypeConverter(TypeMappingContext<S, D> context);

    public <S, D> TypeConverter<S, D> getTypeConverter(Class<S> sourceType, Class<D> destinationType);

}
