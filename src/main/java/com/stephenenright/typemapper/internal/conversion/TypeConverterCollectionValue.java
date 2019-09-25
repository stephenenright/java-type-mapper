package com.stephenenright.typemapper.internal.conversion;

import java.util.LinkedList;
import java.util.List;

import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConditionalConverter;
import com.stephenenright.typemapper.internal.util.CollectionUtils;

class TypeConverterCollectionValue {

    private TypeConverter<?, ?> converter;
    private List<TypeConditionalConverter<?, ?>> predicateConverterList = new LinkedList<>();

    public TypeConverterCollectionValue(TypeConverter<?, ?> converter,
            TypeConditionalConverter<?, ?>... predicateConverters) {
        this.converter = converter;
        this.predicateConverterList = CollectionUtils.asLinkedList(predicateConverters);
    }

    public TypeConverter<?, ?> getConverter() {
        return converter;
    }

    public void setConverter(TypeConverter<?, ?> converter) {
        this.converter = converter;
    }

    public void addPredicateConverter(TypeConditionalConverter<?, ?> converter) {
        predicateConverterList.add(converter);
    }

    public boolean hasPredicateConverters() {
        return !predicateConverterList.isEmpty();

    }

    public List<TypeConditionalConverter<?, ?>> getPredicateConverterList() {
        return predicateConverterList;
    }

    public void setPredicateConverterList(List<TypeConditionalConverter<?, ?>> predicateConverterList) {
        this.predicateConverterList = predicateConverterList;
    }
}
