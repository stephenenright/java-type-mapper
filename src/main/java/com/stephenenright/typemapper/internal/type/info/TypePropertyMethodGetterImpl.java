package com.stephenenright.typemapper.internal.type.info;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.stephenenright.typemapper.exception.PropertyGetterException;
import com.stephenenright.typemapper.internal.common.error.Errors;

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
            throw new Errors().errorGettingPropertyValue(member, e).toPropertyGetterMappingException();

        }
    }

    @Override
    public String toString() {
        return member == null ? name : member.getDeclaringClass().getSimpleName() + "." + name;
    }

}
