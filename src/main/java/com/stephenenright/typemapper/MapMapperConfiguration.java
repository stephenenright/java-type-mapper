package com.stephenenright.typemapper;

import com.stephenenright.typemapper.internal.configuration.MapMapperConfigurationImpl;

/**
 * Configuration used to to configure an individual type mapping
 */
public interface MapMapperConfiguration extends TypeMapperConfiguration {

    /**
     * Create a new configuration instance
     * 
     * @return the configuration instance
     */
    public static MapMapperConfiguration create() {
        return new MapMapperConfigurationImpl();
    }

}
