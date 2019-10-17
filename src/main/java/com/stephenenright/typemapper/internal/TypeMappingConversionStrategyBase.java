package com.stephenenright.typemapper.internal;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.internal.type.info.TypePropertyGetter;
import com.stephenenright.typemapper.internal.type.mapping.TypeMapping;

abstract class TypeMappingConversionStrategyBase {

    protected <S, D> TypeConverter<S, D> getTypeConverterFromContext(TypeMappingContextImpl<S, D> context) {
        return context.getTypeConverter(context.getSourceType(), context.getDestinationType());
    }

    protected Object resolveSourceValue(TypeMapping mapping, TypeMappingContext<?, ?> context) {
        Object source = context.getSource();

        for (TypePropertyGetter accessor : mapping.getSourceGetters()) {
            source = accessor.getValue(source);
        }

        return source;
    }

}
