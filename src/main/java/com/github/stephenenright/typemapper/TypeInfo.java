package com.github.stephenenright.typemapper;

import java.util.Map;

import com.github.stephenenright.typemapper.internal.type.info.TypePropertyGetter;
import com.github.stephenenright.typemapper.internal.type.info.TypePropertySetter;

/**
 * Describes a type for the given configuration
 *
 * @param <T> the type that this object describes
 */
public interface TypeInfo<T> {

    /**
     * Returns the type of the object described
     * 
     * @return the described objects class
     */
    Class<T> getType();

    /**
     * Returns the configuration used to describe this type
     * 
     * @return the configuration
     */
    TypeMapperConfiguration getConfiguration();

    /**
     * Returns the object property getters of the described type
     * 
     * @return A
     *         <code> Map<String, TypePropertyGetter> of getter names to <code>TypePropertyGetter</code>
     *         instances.
     */
    Map<String, TypePropertyGetter> getPropertyGetters();

    /**
     * Returns the object property setters of the described type
     * 
     * @return A
     *         <code> Map<String, TypePropertySetter> of setter names to <code>TypePropertySetter</code>
     *         instances
     */
    Map<String, TypePropertySetter> getPropertySetters();

    boolean isCacheable();

}