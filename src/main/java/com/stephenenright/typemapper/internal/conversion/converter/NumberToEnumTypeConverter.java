package com.stephenenright.typemapper.internal.conversion.converter;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.factory.NumberToNumberTypeConverterFactory;
import com.stephenenright.typemapper.internal.util.AssertUtils;

@SuppressWarnings("rawtypes")
public class NumberToEnumTypeConverter implements TypeConverter<Number, Enum> {

    public static final NumberToEnumTypeConverter INSTANCE = new NumberToEnumTypeConverter();

    @Override
    public Enum<?> convert(TypeMappingContext<Number, Enum> context) {
        Number value = context.getSource();

        if (value == null) {
            return null;
        }

        Class<?> destinationType = context.getDestinationType();
        AssertUtils.notNull(destinationType, "Target type must not be null");

        TypeValueConverter<Number, Integer> converter = NumberToNumberTypeConverterFactory.INSTANCE
                .getTypeValueConverter(Integer.class);

        if (converter == null) {
            return null;
        }

        Integer enumValue = converter.convertValue(value);

        if (enumValue == null) {
            return null;
        }

        try {
            return (Enum<?>) destinationType.getEnumConstants()[enumValue];
        } catch (Exception e) {

        }

        return null;
    }

}
