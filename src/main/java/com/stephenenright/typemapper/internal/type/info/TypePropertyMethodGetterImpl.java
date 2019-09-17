package com.stephenenright.typemapper.internal.type.info;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.stephenenright.typemapper.exception.PropertyGetterException;

class TypePropertyMethodGetterImpl extends TypePropertyInfoBase<Method> implements TypePropertyGetter {

    public TypePropertyMethodGetterImpl(String name, Class<?> type, Method method) {
        super(name, type, method);
    }

    @Override
    public Type getGenericType() {
        return member.getGenericReturnType();
    }

    @Override
    public Object getValue(Object source) {
        try {
            return member.invoke(source);
        } catch (Exception e) {
            throw new PropertyGetterException("Unable to get value for method: " + member.getName() + ", for type: "
                    + member.getDeclaringClass().getName(), e);
        }
    }
}
