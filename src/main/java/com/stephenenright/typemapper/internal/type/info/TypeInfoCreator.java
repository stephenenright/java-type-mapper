package com.stephenenright.typemapper.internal.type.info;

import com.stephenenright.typemapper.TypeMapperConfiguration;

public interface TypeInfoCreator {

    public <T> TypeInfo<T> create(Class<T> type, TypeMapperConfiguration configuration);
}
