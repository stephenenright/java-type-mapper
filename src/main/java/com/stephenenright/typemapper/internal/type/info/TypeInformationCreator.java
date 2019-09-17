package com.stephenenright.typemapper.internal.type.info;

import com.stephenenright.typemapper.configuration.TypeMapperConfiguration;

public interface TypeInformationCreator {

    public <T> TypeInformation<T> create(Class<T> type, TypeMapperConfiguration configuration);
}
