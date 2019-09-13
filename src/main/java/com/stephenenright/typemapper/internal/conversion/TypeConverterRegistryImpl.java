package com.stephenenright.typemapper.internal.conversion;

import java.util.List;

import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConverterFactory;

public class TypeConverterRegistryImpl implements TypeConverterRegistry {

    private TypeConverterCollection converterCollection;

    public TypeConverterRegistryImpl() {
        this.converterCollection = new TypeConverterCollectionImpl();
    }

    @Override
    public TypeConverter<?, ?> getConverter(Class<?> sourceType, Class<?> destinationType) {
        return converterCollection.findConverter(sourceType, destinationType);
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
