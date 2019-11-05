package com.stephenenright.typemapper.internal.type.info;

import java.util.Map;

import com.stephenenright.typemapper.TypeInfo;
import com.stephenenright.typemapper.TypeInfoRegistry;
import com.stephenenright.typemapper.TypeMapperConfiguration;
import com.stephenenright.typemapper.internal.common.Pair;
import com.stephenenright.typemapper.internal.util.JavaBeanUtils;

public class TypeInfoCreatorDefaultImpl implements TypeInfoCreator {

    private TypePropertyInfoCollector propertyInfoCollector;

    public TypeInfoCreatorDefaultImpl(TypePropertyInfoCollector propertyInfoCollector) {
        this.propertyInfoCollector = propertyInfoCollector;
    }

    @Override
    public <T> TypeInfo<T> create(T source, Class<T> type, TypeMapperConfiguration configuration,
            TypeInfoRegistry registry) {
        if (source != null && Map.class.isAssignableFrom(source.getClass())) {
            return new TypeInfoMapImpl<T>(source, type, configuration, null, null, registry);
        }

        Pair<Map<String, TypePropertyGetter>, Map<String, TypePropertySetter>> propertyPair = propertyInfoCollector
                .collectProperties(source, type, configuration, registry);

        return new TypeInfoImpl<T>(type, configuration, propertyPair.getValue1(), propertyPair.getValue2());

    }

    @Override
    public boolean isPossibleTypeHasProperties(Class<?> type) {
        boolean isPossible = JavaBeanUtils.isPossibleJavaBean(type);

        if (!isPossible) {
            return Map.class.isAssignableFrom(type);
        }

        return isPossible;
    }
}
