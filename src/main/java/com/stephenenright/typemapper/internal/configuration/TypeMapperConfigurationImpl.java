package com.stephenenright.typemapper.internal.configuration;

import com.stephenenright.typemapper.TypeAccessLevel;
import com.stephenenright.typemapper.TypeMapperConfiguration;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConverterFactory;
import com.stephenenright.typemapper.internal.conversion.TypeConverterCollectionImpl;
import com.stephenenright.typemapper.internal.conversion.TypeConverterRegistry;
import com.stephenenright.typemapper.internal.conversion.TypeConverterRegistryImpl;
import com.stephenenright.typemapper.internal.util.AssertUtils;

public class TypeMapperConfigurationImpl implements TypeMapperConfiguration {

    private TypeAccessLevel accessLevel;
    private TypeConverterCollectionImpl converterCollection;
    private TypeConverterRegistry converterRegistry;

    public TypeMapperConfigurationImpl() {
        accessLevel = TypeAccessLevel.PUBLIC;
        converterCollection = new TypeConverterCollectionImpl();
        converterRegistry = new TypeConverterRegistryImpl(converterCollection);
    }

    public TypeAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(TypeAccessLevel accessLevel) {
        AssertUtils.notNull(accessLevel, "Access Level cannot be null");
        this.accessLevel = accessLevel;
    }

    @Override
    public <S, D> void addTypeConverter(TypeConverter<S, D> typeConverter) {
        converterCollection.add(typeConverter);
    }

    @Override
    public <S, D> void addTypeConverterFactory(TypeConverterFactory<S, D> factory) {
        converterCollection.add(factory);
    }

    public <S, D> TypeConverter<S, D> getTypeConverter(Class<S> sourceType, Class<D> destinationType) {
        return converterRegistry.getConverter(sourceType, destinationType);

    }

}
