package com.stephenenright.typemapper.internal.type.info;

import java.util.Map;

import com.stephenenright.typemapper.TypeInfo;
import com.stephenenright.typemapper.TypeMapperConfiguration;

class TypeInfoBaseImpl<T> implements TypeInfo<T> {

    protected final Class<T> type;
    protected final TypeMapperConfiguration configuration;
    protected Map<String, TypePropertyGetter> propertyGetters;
    protected Map<String, TypePropertySetter> propertySetters;

    public TypeInfoBaseImpl(Class<T> type, TypeMapperConfiguration configuration,
            Map<String, TypePropertyGetter> propertyGetters, Map<String, TypePropertySetter> propertySetters) {
        this.type = type;
        this.configuration = configuration;
        this.propertyGetters = propertyGetters;
        this.propertySetters = propertySetters;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public TypeMapperConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public Map<String, TypePropertyGetter> getPropertyGetters() {
        return propertyGetters;
    }

    @Override
    public Map<String, TypePropertySetter> getPropertySetters() {
        return propertySetters;
    }

    @Override
    public boolean isCacheable() {
        return true;
    }
}
