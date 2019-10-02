package com.stephenenright.typemapper.internal.type.info;

import java.lang.reflect.Member;
import java.lang.reflect.Type;

import com.stephenenright.typemapper.internal.util.TypeUtils;

abstract class TypePropertyInfoBase<M extends Member> implements TypePropertyInfo {

    protected Class<?> typeInDeclaringClass;
    protected String name;
    protected Class<?> type;
    protected final M member;

    public TypePropertyInfoBase(String name, Class<?> type, M member) {
        this.typeInDeclaringClass = type;
        this.name = name;
        this.member = member;
        this.type = resolveType(type);
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }
    
    
    @Override
    public Class<?> getTypeInDeclaringClass() {
       return typeInDeclaringClass;
    }

    private Class<?> resolveType(Class<?> type) {
        Type genericType = getGenericType();

        if (genericType == null) {
            return type;
        }

        return TypeUtils.resolveRawClass(genericType, type);

    }
}
