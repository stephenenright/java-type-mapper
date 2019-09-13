package com.stephenenright.typemapper.converter;

import com.stephenenright.typemapper.TypeMappingContext;

public interface TypeConverterFactory<S, ST> {
    <T extends ST> TypeConverter<S, T> getTypeConverter(TypeMappingContext<?, ?> context);
}
