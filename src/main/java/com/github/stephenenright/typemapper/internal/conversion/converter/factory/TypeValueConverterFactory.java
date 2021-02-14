package com.github.stephenenright.typemapper.internal.conversion.converter.factory;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.internal.conversion.converter.TypeValueConverter;

public interface TypeValueConverterFactory<S, ST> {
    <T extends ST> TypeValueConverter<S, T> getTypeValueConverter(TypeMappingContext<?, ?> context);

    <T extends ST> TypeValueConverter<S, T> getTypeValueConverter(Class<T> destinationType);
}
