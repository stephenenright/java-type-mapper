package com.stephenenright.typemapper.internal.type.info;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.stephenenright.typemapper.exception.PropertySetterException;

class TypePropertyMethodSetterImpl extends TypePropertyInfoBase<Method> implements TypePropertySetter {

    public TypePropertyMethodSetterImpl(String name, Class<?> type, Method method) {
        super(name, type, method);
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
            throw new PropertySetterException("Error setting value using method: " + member.getName() + ", for type: "
                    + member.getDeclaringClass().getName(), e);
        }
    }

    @Override
    public String toString() {
        return member == null ? name : member.getDeclaringClass().getSimpleName() + "." + name;
    }
    
}
