package com.github.stephenenright.typemapper.internal.type.info;

import com.github.stephenenright.typemapper.TypeInfo;
import com.github.stephenenright.typemapper.TypeInfoRegistry;
import com.github.stephenenright.typemapper.TypeMapperConfiguration;

public interface TypeInfoCreator {

    public <T> TypeInfo<T> create(T source, Class<T> type, TypeMapperConfiguration configuration, TypeInfoRegistry registry);

    public boolean isPossibleTypeHasProperties(Class<?> type);
}
