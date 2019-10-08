package com.stephenenright.typemapper.internal.type.mapping;

import java.util.LinkedList;
import java.util.List;

import com.stephenenright.typemapper.TypeMapperConfiguration;
import com.stephenenright.typemapper.converter.TypeConverter;

public class TypeMappingInfoImpl<S, D> implements TypeMappingInfo<S, D> {

    private final Class<S> sourceType;
    private final Class<D> destinationType;
    private final TypeMapperConfiguration configuration;
    private TypeConverter<S, D> converter;
    private List<TypeMapping> mappings;

    public TypeMappingInfoImpl(Class<S> sourceType, Class<D> destinationType, TypeMapperConfiguration configuration) {
        this.sourceType = sourceType;
        this.destinationType = destinationType;
        this.configuration = configuration;
        this.mappings = new LinkedList<TypeMapping>();
    }

    @Override
    public Class<D> getDestinationType() {
        return destinationType;
    }

    @Override
    public Class<S> getSourceType() {
        return sourceType;
    }

    @Override
    public TypeMapperConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public List<TypeMapping> getTypeMappings() {
        return mappings;
    }
    
    @Override
    public void addMapping(TypeMapping mapping) {
        this.mappings.add(mapping);
    }
    
    @Override
    public void addMappings(List<TypeMapping> mappings) {
        for(TypeMapping mapping: mappings) {
            this.mappings.add(mapping);
        }
    }

    @Override
    public TypeConverter<S, D> getConverter() {
        return converter;
    }

    public void setConverter(TypeConverter<S, D> converter) {
        this.converter = converter;
    }

}
