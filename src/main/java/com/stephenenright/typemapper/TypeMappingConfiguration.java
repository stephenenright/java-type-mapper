package com.stephenenright.typemapper;

import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConverterFactory;
import com.stephenenright.typemapper.internal.configuration.TypeMappingConfigurationImpl;

/**
 * Configuration that can be used to configure the underlying type mapping
 * service
 */
public interface TypeMappingConfiguration {

    /**
     * Returns the introspection store used to look up type information
     * 
     * @return the introspection store
     */
    public TypeIntrospector getIntrospector();

    /**
     * Allows the introspector to be set. This is useful if you want to reuse an
     * instrospector cache, for example if the spring framework is being used etc.
     * 
     * @param introspector the introspector to set
     */
    public void setIntrospector(TypeIntrospector introspector);

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
    public static TypeMappingConfiguration create() {
        return new TypeMappingConfigurationImpl();
    }

}
