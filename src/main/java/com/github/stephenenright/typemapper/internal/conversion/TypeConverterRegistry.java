package com.github.stephenenright.typemapper.internal.conversion;

import java.util.List;

import com.github.stephenenright.typemapper.converter.TypeConverter;
import com.github.stephenenright.typemapper.converter.TypeConverterFactory;

public interface TypeConverterRegistry {
    
    public <S, D> TypeConverter<S, D> getConverter(Class<?> sourceType, Class<?> destinationType);
    
    public void registerConverterFactories(List<TypeConverterFactory<?, ?>> factories);

    public void registerConverterFactory(TypeConverterFactory<?, ?> factory);

    public void registerConverters(List<TypeConverter<?, ?>> converters);

    public void registerConverter(TypeConverter<?, ?> converter);
    
    
    
}
