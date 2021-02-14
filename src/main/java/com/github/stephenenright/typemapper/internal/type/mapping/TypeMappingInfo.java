package com.github.stephenenright.typemapper.internal.type.mapping;

import java.util.List;

import com.github.stephenenright.typemapper.TypeMapperConfiguration;
import com.github.stephenenright.typemapper.converter.TypeConverter;

public interface TypeMappingInfo<S, D> {

    public Class<D> getDestinationType();

    public Class<S> getSourceType();

    public TypeMapperConfiguration getConfiguration();

    public List<TypeMapping> getTypeMappings();

    public TypeConverter<S, D> getConverter();

    public void addMapping(TypeMapping mapping);

    public void addMappings(List<TypeMapping> mappings);

    public boolean isCacheable();
    
    public void setCacheable(boolean cacheable);

}
