package com.stephenenright.typemapper.configuration;

import com.stephenenright.typemapper.TypeIntrospector;
import com.stephenenright.typemapper.internal.configuration.TypeMapperConfigurationImpl;

public interface TypeMapperConfiguration {

    /**
     * Returns the base access level at which properties can be mapped.
     * 
     * @return the access level
     */
    public TypeAccessLevel getAccessLevel();

    /**
     * Sets the base access level at which properties can be mapped. Only properties
     * with the access level or higher will be mapped. For example setting PUBLIC
     * will mean only public properties will be mapped, while setting PRIVATE will
     * mean all PUBLIC, PROTECTED, PACKAGE_PRIVATE, PRIVATE properties will be
     * mapped.
     * 
     * @param accessLevel the access level to set
     */
    public void setAccessLevel(TypeAccessLevel accessLevel);

    /**
     * Returns the introspection store used to look up type information
     * 
     * @return the introspection store
     */
    public TypeIntrospector getIntrospector();

    /**
     * Allows the introspector to be set. This is useful if you want to reuse
     * an instrospector cache, for example if the spring framework is being used etc.
     * 
     * @param introspector the introspector to set
     */
    public void setIntrospector(TypeIntrospector introspector);

    /**
     * Create a new configuration instance
     * @return  the configuration instance
     */
    public static TypeMapperConfiguration create() {
        return new TypeMapperConfigurationImpl();
    }

}
