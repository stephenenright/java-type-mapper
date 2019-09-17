package com.stephenenright.typemapper.internal.type.info;

import java.lang.reflect.Type;

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

    /**
     * Returns the generic type of the property
     * 
     * @return the property generic type
     */
    public Type getGenericType();

}
