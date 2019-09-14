package com.stephenenright.typemapper.internal.conversion.converter.factory;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.internal.conversion.converter.TypeValueConverter;

public interface TypeValueConverterFactory<S, ST> {
    <T extends ST> TypeValueConverter<S, T> getTypeValueConverter(TypeMappingContext<?, ?> context);
}
