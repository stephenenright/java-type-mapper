package com.stephenenright.typemapper.internal.type.mapping;

import java.util.List;

import com.stephenenright.typemapper.internal.type.info.TypePropertyGetter;
import com.stephenenright.typemapper.internal.type.info.TypePropertySetter;

public interface TypeMapping {

    public List<TypePropertyGetter> getSourceGetters();

    public List<TypePropertySetter> getDestinationSetters();
    
    public String getSourcePath();
    
    public String getDestinationPath();
    
}
