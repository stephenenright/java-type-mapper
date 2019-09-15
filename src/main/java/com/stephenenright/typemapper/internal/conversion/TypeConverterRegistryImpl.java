package com.stephenenright.typemapper.internal.conversion;

import java.util.List;

import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConverterFactory;

public class TypeConverterRegistryImpl implements TypeConverterRegistry {

    private TypeConverterCollection converterCollection;

    public TypeConverterRegistryImpl() {
        this.converterCollection = new TypeConverterCollectionImpl();
    }

    public TypeConverterRegistryImpl(TypeConverterCollection converterCollection) {
        this.converterCollection = converterCollection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S, D> TypeConverter<S, D> getConverter(Class<?> sourceType, Class<?> destinationType) {
        TypeConverter<?, ?> converter = converterCollection.findConverter(sourceType, destinationType);

        if (converter != null) {
            return (TypeConverter<S, D>) converter;
        }

        return null;

    }

    @Override
    public void registerConverterFactories(List<TypeConverterFactory<?, ?>> factories) {
        for (TypeConverterFactory<?, ?> factory : factories) {
            registerConverterFactory(factory);
        }

    }

    @Override
    public void registerConverterFactory(TypeConverterFactory<?, ?> factory) {
        converterCollection.add(factory);
    }

    @Override
    public void registerConverters(List<TypeConverter<?, ?>> converters) {
        for (TypeConverter<?, ?> converter : converters) {
            registerConverter(converter);
        }

    }

    @Override
    public void registerConverter(TypeConverter<?, ?> converter) {
        converterCollection.add(converter);

    }
}
