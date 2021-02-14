package com.github.stephenenright.typemapper.internal;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;
import com.github.stephenenright.typemapper.internal.type.mapping.TypeMapping;
import com.github.stephenenright.typemapper.internal.type.info.TypePropertyGetter;

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
