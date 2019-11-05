package com.stephenenright.typemapper.internal.type.info;

import com.stephenenright.typemapper.TypeInfo;
import com.stephenenright.typemapper.TypeInfoRegistry;
import com.stephenenright.typemapper.TypeMapperConfiguration;

public interface TypeInfoCreator {

    public <T> TypeInfo<T> create(T source, Class<T> type, TypeMapperConfiguration configuration, TypeInfoRegistry registry);

    public boolean isPossibleTypeHasProperties(Class<?> type);
}
