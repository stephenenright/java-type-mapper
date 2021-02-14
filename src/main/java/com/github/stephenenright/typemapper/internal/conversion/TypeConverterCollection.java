package com.github.stephenenright.typemapper.internal.conversion;

import com.github.stephenenright.typemapper.converter.TypeConverter;
import com.github.stephenenright.typemapper.converter.TypeConverterFactory;

public interface TypeConverterCollection {

    public void add(TypeConverter<?, ?> converter);

    public void add(TypeConverterFactory<?, ?> factory);

    public TypeConverter<?, ?> findConverter(Class<?> sourceType, Class<?> destinationType);

    public boolean isEmpty();

}
