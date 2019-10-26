package com.stephenenright.typemapper;

import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConverterFactory;

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
     * Returns a type converter for the given source and destination type
     */
    public <S, D> TypeConverter<S, D> getTypeConverter(Class<S> sourceType, Class<D> destinationType);

    /**
     * Determines if there are include mappings configured
     */
    public boolean hasIncludeMappings();

    /**
     * Set the properties that will only be included in the mapping
     * 
     * @param properties the property paths
     */
    public void addIncludeMapping(String... properties);

    /**
     * Determines if there are exclude mappings configured
     */
    public boolean hasExcludeMappings();

    /**
     * Set the properties that will only be excluded from the mapping
     * 
     * @param properties the property paths
     */
    public void addExcludeMapping(String... properties);


    /**
     * Returns if the property should be included in the mapping
     */
    public boolean isMappingIncluded(String parentPath, String propertyPath, Object valueToMap,
            TypeInfoRegistry typeInfoRegistry);

}
