package com.stephenenright.typemapper.internal.type.mapping;

import java.util.List;

import com.stephenenright.typemapper.configuration.TypeMapperConfiguration;
import com.stephenenright.typemapper.converter.TypeConverter;

public interface TypeMappingInfo<S, D> {

    public Class<D> getDestinationType();

    public Class<S> getSourceType();

    public TypeMapperConfiguration getConfiguration();

    public List<TypeMapping> getTypeMappings();

    public TypeConverter<S, D> getConverter();

    public void addMapping(TypeMapping mapping);
    
    public void addMappings(List<TypeMapping> mappings);

}
