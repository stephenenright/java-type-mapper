package com.stephenenright.typemapper.internal.type.mapping;

import java.lang.reflect.Type;
import java.util.List;

import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.internal.type.info.TypePropertyGetter;
import com.stephenenright.typemapper.internal.type.info.TypePropertySetter;

public interface TypeMapping {

    public List<TypePropertyGetter> getSourceGetters();

    public List<TypePropertySetter> getDestinationSetters();
    
    public String getSourcePath();
    
    public String getDestinationPath();
    
    public Class<?> getSourceType();
    
    public Class<?> getDesintationType();
    
    public Type getDesintationGenericType();
    
    public TypeConverter<?, ?> getConverter();
    
}
