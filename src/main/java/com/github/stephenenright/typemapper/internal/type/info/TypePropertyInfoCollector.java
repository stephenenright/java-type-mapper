package com.github.stephenenright.typemapper.internal.type.info;

import java.util.Map;

import com.github.stephenenright.typemapper.TypeInfoRegistry;
import com.github.stephenenright.typemapper.TypeMapperConfiguration;
import com.github.stephenenright.typemapper.internal.common.Pair;

public interface TypePropertyInfoCollector {

    public Pair<Map<String, TypePropertyGetter>, Map<String, TypePropertySetter>> collectProperties(Object source, Class<?> type,
                                                                                                    TypeMapperConfiguration configuration, TypeInfoRegistry registry);

}
