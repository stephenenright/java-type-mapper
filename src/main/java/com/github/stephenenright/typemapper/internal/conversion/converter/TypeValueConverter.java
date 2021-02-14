package com.github.stephenenright.typemapper.internal.conversion.converter;

public interface TypeValueConverter<S, D> {
    public D convertValue(S value);
}
