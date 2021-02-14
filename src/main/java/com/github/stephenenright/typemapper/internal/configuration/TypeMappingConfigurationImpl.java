package com.github.stephenenright.typemapper.internal.configuration;

import com.github.stephenenright.typemapper.TypeIntrospector;
import com.github.stephenenright.typemapper.TypeMappingConfiguration;
import com.github.stephenenright.typemapper.converter.TypeConverter;
import com.github.stephenenright.typemapper.converter.TypeConverterFactory;
import com.github.stephenenright.typemapper.internal.conversion.TypeConverterCollectionImpl;
import com.github.stephenenright.typemapper.internal.type.info.TypeIntrospectorImpl;
import com.github.stephenenright.typemapper.internal.util.AssertUtils;

public class TypeMappingConfigurationImpl implements TypeMappingConfiguration {

    private TypeIntrospector introspectionStore = new TypeIntrospectorImpl();

    private TypeConverterCollectionImpl converterCollection = new TypeConverterCollectionImpl();

    @Override
    public TypeIntrospector getIntrospector() {
        return introspectionStore;
    }

    @Override
    public void setIntrospector(TypeIntrospector introspector) {
        AssertUtils.notNull(introspector);
        this.introspectionStore = introspector;
    }

    @Override
    public <S, D> void addTypeConverter(TypeConverter<S, D> typeConverter) {
        converterCollection.add(typeConverter);
    }

    @Override
    public <S, D> void addTypeConverterFactory(TypeConverterFactory<S, D> factory) {
        converterCollection.add(factory);

    }

    public TypeConverterCollectionImpl getConverterCollection() {
        return converterCollection;
    }

}
