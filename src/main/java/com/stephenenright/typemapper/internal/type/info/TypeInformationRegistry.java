package com.stephenenright.typemapper.internal.type.info;

import com.stephenenright.typemapper.configuration.TypeMapperConfiguration;

public interface TypeInformationRegistry {

    public <T> TypeInformation<T> get(Class<T> type, TypeMapperConfiguration configuration);

}
