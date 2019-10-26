package com.stephenenright.typemapper.internal.configuration;

import java.util.HashSet;
import java.util.Set;

import com.stephenenright.typemapper.TypeAccessLevel;
import com.stephenenright.typemapper.TypeInfoRegistry;
import com.stephenenright.typemapper.TypeMapperConfiguration;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConverterFactory;
import com.stephenenright.typemapper.internal.common.CommonConstants;
import com.stephenenright.typemapper.internal.conversion.TypeConverterCollectionImpl;
import com.stephenenright.typemapper.internal.conversion.TypeConverterRegistry;
import com.stephenenright.typemapper.internal.conversion.TypeConverterRegistryImpl;
import com.stephenenright.typemapper.internal.util.AssertUtils;
import com.stephenenright.typemapper.internal.util.IterableUtils;
import com.stephenenright.typemapper.internal.util.JavaBeanUtils;
import com.stephenenright.typemapper.internal.util.MapUtils;
import com.stephenenright.typemapper.internal.util.PropertyPathUtils;
import com.stephenenright.typemapper.internal.util.StringUtils;

class TypeMapperConfigurationBase implements TypeMapperConfiguration {

    private TypeAccessLevel accessLevel;
    private TypeConverterCollectionImpl converterCollection;
    private TypeConverterRegistry converterRegistry;
    private Set<String> includeMappings;
    private Set<String> parentIncludeMappings;
    private Set<String> descendantIncludeMappings;
    private Set<String> excludeMappings;

    public TypeMapperConfigurationBase() {
        super();
        accessLevel = TypeAccessLevel.PUBLIC;
        converterCollection = new TypeConverterCollectionImpl();
        converterRegistry = new TypeConverterRegistryImpl(converterCollection);
        includeMappings = new HashSet<>();
        parentIncludeMappings = new HashSet<String>();
        excludeMappings = new HashSet<String>();
    }

    public TypeAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(TypeAccessLevel accessLevel) {
        AssertUtils.notNull(accessLevel, "Access Level cannot be null");
        this.accessLevel = accessLevel;
    }

    @Override
    public <S, D> void addTypeConverter(TypeConverter<S, D> typeConverter) {
        converterCollection.add(typeConverter);
    }

    @Override
    public <S, D> void addTypeConverterFactory(TypeConverterFactory<S, D> factory) {
        converterCollection.add(factory);
    }

    @Override
    public <S, D> TypeConverter<S, D> getTypeConverter(Class<S> sourceType, Class<D> destinationType) {
        return converterRegistry.getConverter(sourceType, destinationType);
    }

    @Override
    public void addIncludeMapping(String... properties) {
        for (String property : properties) {
            addIncludeMapping(property);
        }
    }

    private void addIncludeMapping(String property) {
        if (StringUtils.isNullOrEmpty(property)) {
            return;
        }

        char lastChar = property.charAt(property.length() - 1);

        if (lastChar == CommonConstants.PROPERTY_PATH_ALL_CHAR) {
            if(property.length() >=2) {
                char secondLastChar =  property.charAt(property.length() - 2);
                
                //check for descendant selector
                if (secondLastChar == CommonConstants.PROPERTY_PATH_ALL_CHAR) {
                    if(descendantIncludeMappings==null) {
                        descendantIncludeMappings = new HashSet<>();
                    }
                    
                    String parentPath = PropertyPathUtils.getParentPath(property);
                    descendantIncludeMappings.add(parentPath);
                    return;
                }
            }
           
            //add parent path because no descendant path detected
            String parentPath = PropertyPathUtils.getParentPath(property);
            parentIncludeMappings.add(parentPath);
            includeMappings.add(parentPath);
            
        } else {
            includeMappings.add(property);
        }
    }

    @Override
    public boolean hasIncludeMappings() {
        return !includeMappings.isEmpty();
    }

   
    @Override
    public boolean hasExcludeMappings() {
        return !excludeMappings.isEmpty();
    }

    @Override
    public void addExcludeMapping(String... properties) {
        for(String property: properties) {
            excludeMappings.add(property);
        }
    }

    //TODO REVISE
    @Override
    public boolean isMappingIncluded(String parentPath, String propertyPath, Object valueToMap,
            TypeInfoRegistry typeInfoRegistry) {
        
        if(isMappingExcluded(propertyPath)) {
            return false;
        }
        
        if(descendantIncludeMappings!=null) {
            for(String descendantInclude: descendantIncludeMappings) {
               if(propertyPath.startsWith(descendantInclude)) {
                   return true;
               }
            }
        }
        
        if (includeMappings.isEmpty()) {
            return true;
        }

        if (includeMappings.contains(propertyPath)) {
            return true;
        }

        if (parentIncludeMappings.isEmpty()) {
            return false;
        }

        if (parentIncludeMappings.contains(parentPath)) {
            Class<?> valueToMapType = valueToMap.getClass();
            
            if(IterableUtils.isIterable(valueToMapType) || MapUtils.isMap(valueToMapType)) {
                return false;
            }
 
            if (JavaBeanUtils.isPossibleJavaBean(valueToMapType, typeInfoRegistry, this)) {
                return false;
            }

            return true;

        }

        return false;
    }
    
    
    private boolean isMappingExcluded(String propertyPath) {
        if(excludeMappings.isEmpty()) {
            return false;
        }
        
        for(String excludePath: excludeMappings) {
            if(propertyPath.startsWith(excludePath)) {
                return true;
            }
        }
        
        return false;
    }
    
}
