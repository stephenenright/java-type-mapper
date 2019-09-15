package com.stephenenright.typemapper.internal.conversion.converter;

import java.time.LocalDate;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.internal.util.JavaTimeUtils;

public class LocalDateToStringTypeConverter implements TypeConverter<LocalDate, String> {

    @Override
    public String convert(TypeMappingContext<LocalDate, String> context) {
        LocalDate value = context.getSource();

        if (value == null) {
            return null;
        }

        return JavaTimeUtils.formatAsDateOffsetISO8601(value);
    }
}
