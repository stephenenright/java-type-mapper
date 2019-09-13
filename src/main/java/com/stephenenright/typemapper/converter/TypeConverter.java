package com.stephenenright.typemapper.converter;

import com.stephenenright.typemapper.TypeMappingContext;

public interface TypeConverter<S, D> {
    public D convert(TypeMappingContext<S, D> context);
}
