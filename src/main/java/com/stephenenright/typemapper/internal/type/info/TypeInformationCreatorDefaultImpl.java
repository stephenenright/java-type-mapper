package com.stephenenright.typemapper.internal.type.info;

import java.util.Map;

import com.stephenenright.typemapper.configuration.TypeMapperConfiguration;
import com.stephenenright.typemapper.internal.common.Pair;

public class TypeInformationCreatorDefaultImpl implements TypeInformationCreator {

    private TypePropertyInfoCollector propertyInfoCollector;

    public TypeInformationCreatorDefaultImpl(TypePropertyInfoCollector propertyInfoCollector) {
        this.propertyInfoCollector = propertyInfoCollector;

    }

    @Override
    public <T> TypeInformation<T> create(Class<T> type, TypeMapperConfiguration configuration) {

        Pair<Map<String, TypePropertyGetter>, Map<String, TypePropertySetter>> propertyPair = propertyInfoCollector
                .collectProperties(type, configuration);

        return new TypeInformationImpl<T>(type, configuration, propertyPair.getValue1(), propertyPair.getValue2());

    }

}
