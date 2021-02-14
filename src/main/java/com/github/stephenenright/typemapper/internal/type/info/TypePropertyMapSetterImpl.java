package com.github.stephenenright.typemapper.internal.type.info;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

import com.github.stephenenright.typemapper.TypeInfo;
import com.github.stephenenright.typemapper.TypeInfoRegistry;
import com.github.stephenenright.typemapper.TypeMapperConfiguration;

class TypePropertyMapSetterImpl extends TypePropertyInfoBase<Method> implements TypePropertySetter {

    private Map<String, Object> source;

    public TypePropertyMapSetterImpl(Map<String, Object> source, String name, Class<?> type, Method method,
            TypeInfoRegistry typeInfoRegistry) {
        super(name, type, method, typeInfoRegistry);
        this.source = source;
    }

    @Override
    public Type getGenericType() {
        return type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setValue(Object target, Object value) {
        if (target == null) {
            return;
        }

        Map<String, Object> map = (Map<String, Object>) target;
        map.put(name, value);

    }

    @Override
    public TypeInfo<?> getTypeInfo(TypeMapperConfiguration configuration) {
        if (source != null) {
            return typeInfoRegistry.get(source, Map.class, configuration);
        }

        return typeInfoRegistry.get(type, configuration);
    }

}
