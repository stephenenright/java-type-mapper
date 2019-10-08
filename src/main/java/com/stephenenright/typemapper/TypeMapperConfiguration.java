package com.stephenenright.typemapper;

import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConverterFactory;
import com.stephenenright.typemapper.internal.configuration.TypeMapperConfigurationImpl;

/**
 * Configuration used to to configure an individual type mapping
 */
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
     * Add a type converter
     */
    public <S, D> void addTypeConverter(TypeConverter<S, D> typeConverter);

    /**
     * Add a type converter factory
     */
    public <S, D> void addTypeConverterFactory(TypeConverterFactory<S, D> factory);

    /**
     * Create a new configuration instance
     * 
     * @return the configuration instance
     */
    public static TypeMapperConfiguration create() {
        return new TypeMapperConfigurationImpl();
    }
}
