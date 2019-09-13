package com.stephenenright.typemapper.internal.conversion;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypePredicateConverter;

class TypeConverterCollectionValue {

    private TypeConverter<?, ?> converter;
    private List<TypePredicateConverter<?, ?>> predicateConverterList = new LinkedList<>();

    public TypeConverterCollectionValue(TypeConverter<?, ?> converter,
            TypePredicateConverter<?, ?>... predicateConverters) {
        this.converter = converter;
        this.predicateConverterList = Arrays.asList(predicateConverters);
    }

    public TypeConverter<?, ?> getConverter() {
        return converter;
    }

    public void setConverter(TypeConverter<?, ?> converter) {
        this.converter = converter;
    }

    public void addPredicateConverter(TypePredicateConverter<?, ?> converter) {
        predicateConverterList.add(converter);
    }

    public boolean hasPredicateConverters() {
        return !predicateConverterList.isEmpty();

    }

    public List<TypePredicateConverter<?, ?>> getPredicateConverterList() {
        return predicateConverterList;
    }

    public void setPredicateConverterList(List<TypePredicateConverter<?, ?>> predicateConverterList) {
        this.predicateConverterList = predicateConverterList;
    }
}
