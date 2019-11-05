package com.stephenenright.typemapper.internal.type.info;

import java.util.Map;

import com.stephenenright.typemapper.TypeInfoRegistry;
import com.stephenenright.typemapper.TypeMapperConfiguration;
import com.stephenenright.typemapper.internal.common.Pair;

public interface TypePropertyInfoCollector {

    public Pair<Map<String, TypePropertyGetter>, Map<String, TypePropertySetter>> collectProperties(Object source, Class<?> type,
            TypeMapperConfiguration configuration, TypeInfoRegistry registry);

}
