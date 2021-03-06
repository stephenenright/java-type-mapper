package com.github.stephenenright.typemapper;

import com.github.stephenenright.typemapper.converter.TypeConverter;
import com.github.stephenenright.typemapper.converter.TypeConverterFactory;

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
     * @param typeConverter the type converter to add
     * @param <S>
     * @param <D>
     */
    public <S, D> void addTypeConverter(TypeConverter<S, D> typeConverter);


    /**
     * Add a type converter factory
     * @param factory
     * @param <S>
     * @param <D>
     */
    public <S, D> void addTypeConverterFactory(TypeConverterFactory<S, D> factory);

    /**
     * Adds a transformer to transform the given property path
     * @param propertyPath
     * @param transformer
     */
    public void addPropertyTransformer(String propertyPath, TypePropertyTransformer transformer);


    /**
     * Returns a <code>TypePropertyTransformer</code> if it exists, otherwise null
     * @param propertyPath
     * @return
     */
    public TypePropertyTransformer getPropertyTransformer(String propertyPath);

    /**
     *  Adds a post transformer that allows the transformation of the mapped
     *  destination after the mapping is complete
     * @param transformer
     * @param <S>
     * @param <D>
     */
    public <S, D> void setPostTransformer(TypeTransformer<S, D> transformer);

    /**
     * Returns the configured post transformer
     * @param <S>
     * @param <D>
     * @return
     */
    public <S, D> TypeTransformer<S, D> getPostTransformer();

    /**
     * Returns a type converter for the given source and destination type
     * @param sourceType
     * @param destinationType
     * @param <S>
     * @param <D>
     * @return
     */
    public <S, D> TypeConverter<S, D> getTypeConverter(Class<S> sourceType, Class<D> destinationType);

    /**
     * Determines if there are include mappings configured
     * @return
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
     * @return
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
     * @param parentPath
     * @param propertyPath
     * @param valueToMap
     * @param typeInfoRegistry
     * @return
     */
    public boolean isMappingIncluded(String parentPath, String propertyPath, Object valueToMap,
            TypeInfoRegistry typeInfoRegistry);

}
