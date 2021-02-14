package com.github.stephenenright.typemapper.converter;

import com.github.stephenenright.typemapper.TypeMappingContext;

public interface TypeConverterFactory<S, ST> {
    <T extends ST> TypeConverter<S, T> getTypeConverter(TypeMappingContext<?, ?> context);
}
