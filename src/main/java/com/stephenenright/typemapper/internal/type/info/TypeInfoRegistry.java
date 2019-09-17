package com.stephenenright.typemapper.internal.type.info;

import com.stephenenright.typemapper.configuration.TypeMapperConfiguration;

public interface TypeInfoRegistry {

    public <T> TypeInfo<T> get(Class<T> type, TypeMapperConfiguration configuration);

}
