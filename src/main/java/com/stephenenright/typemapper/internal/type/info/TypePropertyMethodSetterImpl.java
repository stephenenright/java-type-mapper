package com.stephenenright.typemapper.internal.type.info;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.stephenenright.typemapper.TypeInfoRegistry;
import com.stephenenright.typemapper.internal.common.error.Errors;

class TypePropertyMethodSetterImpl extends TypePropertyInfoBase<Method> implements TypePropertySetter {

    public TypePropertyMethodSetterImpl(String name, Class<?> type, Method method,TypeInfoRegistry typeInfoRegistry) {
        super(name, type, method,typeInfoRegistry);
    }

    @Override
    public Type getGenericType() {
        return member.getGenericParameterTypes()[0];
    }

    @Override
    public void setValue(Object target, Object value) {
        try {
            member.invoke(target, value);
        } catch (Exception e) {
            throw new Errors().errorSettingPropertyValue(member, value, e).toPropertySetterMappingException();
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
