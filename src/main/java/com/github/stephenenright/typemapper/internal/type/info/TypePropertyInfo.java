package com.github.stephenenright.typemapper.internal.type.info;

import java.lang.reflect.Member;
import java.lang.reflect.Type;

import com.github.stephenenright.typemapper.TypeInfo;
import com.github.stephenenright.typemapper.TypeMapperConfiguration;

public interface TypePropertyInfo {

    /**
     * Returns the name of the property
     * 
     * @return the property name
     */
    public String getName();

    /**
     * Returns the type
     * 
     * @return the type
     */
    public Class<?> getType();

    public Class<?> getTypeInDeclaringClass();

    /**
     * Returns the generic type of the property
     * 
     * @return the property generic type
     */
    public Type getGenericType();

    public Member getMember();

    public TypeInfo<?> getTypeInfo(TypeMapperConfiguration configuration);

}
