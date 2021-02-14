package com.github.stephenenright.typemapper.internal.type.info;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

import com.github.stephenenright.typemapper.TypeInfo;
import com.github.stephenenright.typemapper.TypeInfoRegistry;
import com.github.stephenenright.typemapper.TypeMapperConfiguration;

class TypePropertyMapGetterImpl extends TypePropertyInfoBase<Method> implements TypePropertyGetter {

    private Map<String,Object> source;

    public TypePropertyMapGetterImpl(Map<String,Object> source, String name, Class<?> type, Method method, TypeInfoRegistry typeInfoRegistry) {
        super(name, type, method, typeInfoRegistry);
        this.source = source;
    }

    @Override
    public Type getGenericType() {
        return type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getValue(Object source) {
        if (source == null) {
            return null;
        }

        Map<String, Object> map = (Map<String, Object>) source;

        return map.get(name);
    }

    @Override
    public TypeInfo<?> getTypeInfo(TypeMapperConfiguration configuration) {
        if(source!=null) {
            return typeInfoRegistry.get(source, Map.class, configuration);
        }
        
        return typeInfoRegistry.get(type, configuration);
    }
    
    @Override
    public String toString() {
        return name;
    }
}
