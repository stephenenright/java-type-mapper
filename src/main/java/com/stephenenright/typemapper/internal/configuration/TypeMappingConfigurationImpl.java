package com.stephenenright.typemapper.internal.configuration;

import com.stephenenright.typemapper.TypeIntrospector;
import com.stephenenright.typemapper.TypeMappingConfiguration;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConverterFactory;
import com.stephenenright.typemapper.internal.conversion.TypeConverterCollectionImpl;
import com.stephenenright.typemapper.internal.type.info.TypeIntrospectorImpl;
import com.stephenenright.typemapper.internal.util.AssertUtils;

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
