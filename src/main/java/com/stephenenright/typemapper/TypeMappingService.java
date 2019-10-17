package com.stephenenright.typemapper;

import java.util.List;
import java.util.Map;

import com.stephenenright.typemapper.converter.TypeConverter;

public interface TypeMappingService {
    
    public <S, D> D map(S source, Class<D> destination);

    public <S, D> D map(S source, Class<D> destination, TypeMapperConfiguration configuration);
    
    public <S, D extends Map<String,Object>> D mapToMap(S source);

    public <S, D extends Map<String,Object>> D mapToMap(S source, TypeMapperConfiguration configuration);
    
    public <S, D extends Map<String,Object>> List<D> mapToListOfMap(List<S> source);

    public <S, D extends Map<String,Object>> List<D> mapToListOfMap(List<S> source, TypeMapperConfiguration configuration);
 
    public <S, D> D map(TypeMappingContext<S, D> context);

    public <S, D> D createDestination(TypeMappingContext<S, D> context);

    public <D> D createDestination(Class<D> type);

    public <S, D> TypeConverter<S, D> getTypeConverter(TypeMappingContext<S, D> context);

    public <S, D> TypeConverter<S, D> getTypeConverter(Class<S> sourceType, Class<D> destinationType);

}
