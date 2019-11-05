package com.stephenenright.typemapper.internal.type.info;

import java.util.Map;

import com.stephenenright.typemapper.TypeMapperConfiguration;

public class TypeInfoImpl<T> extends TypeInfoBaseImpl<T> {

    public TypeInfoImpl(Class<T> type, TypeMapperConfiguration configuration,
            Map<String, TypePropertyGetter> propertyGetters, Map<String, TypePropertySetter> propertySetters) {
        super(type, configuration, propertyGetters, propertySetters);
    }
}
