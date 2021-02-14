package com.github.stephenenright.typemapper.internal.type.info;

import java.lang.reflect.Member;
import java.lang.reflect.Type;

import com.github.stephenenright.typemapper.TypeInfo;
import com.github.stephenenright.typemapper.TypeInfoRegistry;
import com.github.stephenenright.typemapper.TypeMapperConfiguration;
import com.github.stephenenright.typemapper.internal.util.ClassUtils;
import com.github.stephenenright.typemapper.internal.util.TypeUtils;

abstract class TypePropertyInfoBase<M extends Member> implements TypePropertyInfo {

    protected Class<?> typeInDeclaringClass;
    protected String name;
    protected Class<?> type;
    protected final M member;
    protected TypeInfoRegistry typeInfoRegistry;

    public TypePropertyInfoBase(String name, Class<?> type, M member, TypeInfoRegistry typeInfoRegistry) {
        this.typeInDeclaringClass = type;
        this.name = name;
        this.member = member;
        this.type = resolveType(type);
        this.typeInfoRegistry = typeInfoRegistry;
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

    @Override
    public Member getMember() {
        return member;
    }

    private Class<?> resolveType(Class<?> type) {
        Type genericType = getGenericType();

        if (genericType == null) {
            return ClassUtils.resolvePrimitiveAsWrapperIfNessecary(type);
        }

        return ClassUtils.resolvePrimitiveAsWrapperIfNessecary(TypeUtils.resolveRawClass(genericType, type));

    }

    @Override
    public TypeInfo<?> getTypeInfo(TypeMapperConfiguration configuration) {
        return typeInfoRegistry.get(type, configuration);
    }
}
