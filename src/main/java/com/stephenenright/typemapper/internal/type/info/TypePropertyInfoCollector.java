package com.stephenenright.typemapper.internal.type.info;

import java.util.Map;

import com.stephenenright.typemapper.configuration.TypeMapperConfiguration;
import com.stephenenright.typemapper.internal.common.Pair;

public interface TypePropertyInfoCollector {

    public Pair<Map<String, TypePropertyGetter>, Map<String, TypePropertySetter>> collectProperties(Class<?> type,
            TypeMapperConfiguration configuration);

}
