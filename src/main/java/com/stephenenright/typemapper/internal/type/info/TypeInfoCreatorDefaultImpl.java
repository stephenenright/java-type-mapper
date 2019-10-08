package com.stephenenright.typemapper.internal.type.info;

import java.util.Map;

import com.stephenenright.typemapper.TypeMapperConfiguration;
import com.stephenenright.typemapper.internal.common.Pair;

public class TypeInfoCreatorDefaultImpl implements TypeInfoCreator {

    private TypePropertyInfoCollector propertyInfoCollector;

    public TypeInfoCreatorDefaultImpl(TypePropertyInfoCollector propertyInfoCollector) {
        this.propertyInfoCollector = propertyInfoCollector;

    }

    @Override
    public <T> TypeInfo<T> create(Class<T> type, TypeMapperConfiguration configuration) {

        Pair<Map<String, TypePropertyGetter>, Map<String, TypePropertySetter>> propertyPair = propertyInfoCollector
                .collectProperties(type, configuration);

        return new TypeInfoImpl<T>(type, configuration, propertyPair.getValue1(), propertyPair.getValue2());

    }

}
