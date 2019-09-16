package com.stephenenright.typemapper.configuration;

import com.stephenenright.typemapper.TypeIntrospectionStore;

public interface TypeMapperConfiguration {

    /**
     * Returns the base access level at which properties can be mapped.
     * @return  the access level
     */
    public TypeAccessLevel getAccessLevel();
    
    /**
     * Sets the base access level at which properties can be mapped. Only properties with the access level or higher 
     * will be mapped.  For example setting PUBLIC will mean only public properties will be mapped, while setting
     * PRIVATE will mean all PUBLIC, PROTECTED, PACKAGE_PRIVATE, PRIVATE properties will be mapped.
     * @param accessLevel   the access level to set
     */
    public void setAccessLevel(TypeAccessLevel accessLevel);
     
    
    /**
     * Returns the introspection store used to look up type information
     * @return  the introspection store
     */
    public TypeIntrospectionStore getIntrospectionStore();
    
    
    /**
     * Allows the introspection store to be set.  This is useful if you want to reuse a cache, for example 
     * if the spring framework is being used etc.
     * @param store the store to set
     */
    public void setIntrospectionStore(TypeIntrospectionStore store);
    
}
