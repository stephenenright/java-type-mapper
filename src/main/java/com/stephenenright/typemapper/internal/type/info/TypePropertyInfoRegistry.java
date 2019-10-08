package com.stephenenright.typemapper.internal.type.info;

import com.stephenenright.typemapper.TypeMapperConfiguration;

public interface TypePropertyInfoRegistry {

    public TypePropertyGetter getterFor(Class<?> type, String accessorName, TypeMapperConfiguration configuration);
    public TypePropertySetter setterFor(Class<?> type, String setterName, TypeMapperConfiguration configuration);
    
    
    
}
