package com.github.stephenenright.typemapper.converter;

import com.github.stephenenright.typemapper.TypeMappingContext;

public interface TypeConverter<S, D> {
    public D convert(TypeMappingContext<S, D> context);
}
