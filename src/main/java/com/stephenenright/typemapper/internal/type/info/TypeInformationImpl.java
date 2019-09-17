package com.stephenenright.typemapper.internal.type.info;

import java.util.Map;

import com.stephenenright.typemapper.configuration.TypeMapperConfiguration;

public class TypeInformationImpl<T> implements TypeInformation<T> {
    
    private final Class<T> type;
    private final TypeMapperConfiguration configuration;
    private final Map<String, TypePropertyGetter> propertyGetters;
    private final Map<String, TypePropertySetter> propertySetters;

    public TypeInformationImpl(Class<T> type, TypeMapperConfiguration configuration, 
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
}
