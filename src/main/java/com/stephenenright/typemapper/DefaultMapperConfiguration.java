package com.stephenenright.typemapper;

import com.stephenenright.typemapper.internal.configuration.DefaultMapperConfigurationImpl;

/**
 * Configuration used to to configure an individual type mapping
 */
public interface DefaultMapperConfiguration extends TypeMapperConfiguration {

    /**
     * Create a new configuration instance
     * 
     * @return the configuration instance
     */
    public static DefaultMapperConfiguration create() {
        return new DefaultMapperConfigurationImpl();
    }

}
