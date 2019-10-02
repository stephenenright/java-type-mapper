package com.stephenenright.typemapper.internal.type.mapping;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.internal.type.info.TypePropertyGetter;
import com.stephenenright.typemapper.internal.type.info.TypePropertySetter;
import com.stephenenright.typemapper.internal.util.ListUtils;

public class TypeMappingImpl implements TypeMapping {

    private List<TypePropertyGetter> sourceGetters;
    private List<TypePropertySetter> destinationSetters;
    private TypeConverter<?, ?> converter;

    public TypeMappingImpl(List<TypePropertyGetter> sourceGetters, List<TypePropertySetter> destinationSetters) {
        this(sourceGetters, destinationSetters, null);
    }

    public TypeMappingImpl(List<TypePropertyGetter> sourceGetters, List<TypePropertySetter> destinationSetters,
            TypeConverter<?, ?> converter) {
        this.sourceGetters = new LinkedList<>();
        this.sourceGetters.addAll(sourceGetters);
        this.destinationSetters = new LinkedList<>();
        this.destinationSetters.addAll(destinationSetters);
        this.converter = converter;
    }

    public TypeMappingImpl(TypeMapping mapping, List<TypePropertyGetter> sourceGetters,
            List<TypePropertySetter> destinationSetters) {
        this.sourceGetters = new LinkedList<>();
        this.sourceGetters.addAll(sourceGetters);
        this.sourceGetters.addAll(mapping.getSourceGetters());

        this.destinationSetters = new LinkedList<>();
        this.destinationSetters.addAll(destinationSetters);
        this.destinationSetters.addAll(mapping.getDestinationSetters());
    }

    @Override
    public List<TypePropertyGetter> getSourceGetters() {
        return sourceGetters;
    }

    @Override
    public List<TypePropertySetter> getDestinationSetters() {
        return destinationSetters;
    }

    @Override
    public TypeConverter<?, ?> getConverter() {
        return converter;
    }

    @Override
    public String getSourcePath() {
        return TypeMappingUtils.mappingPropertiesToString(sourceGetters);
    }

    @Override
    public String getDestinationPath() {
        return TypeMappingUtils.mappingPropertiesToString(destinationSetters);
    }

    @Override
    public Class<?> getSourceType() {
        return ListUtils.getLastElement(sourceGetters).getType();
    }
    
    
    @Override
    public Class<?> getDesintationType() {
        return ListUtils.getLastElement(destinationSetters).getType();

    }
  
    @Override
    public Type getDesintationGenericType() {
        return ListUtils.getLastElement(destinationSetters).getGenericType();
    }
    
    @Override
    public String toString() {
        return TypeMappingUtils.mappingToString(this);
    }
}
