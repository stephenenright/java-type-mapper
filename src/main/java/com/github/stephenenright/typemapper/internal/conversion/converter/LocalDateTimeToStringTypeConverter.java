package com.github.stephenenright.typemapper.internal.conversion.converter;

import java.time.LocalDateTime;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;
import com.github.stephenenright.typemapper.internal.util.JavaTimeUtils;

public class LocalDateTimeToStringTypeConverter implements TypeConverter<LocalDateTime, String> {

    public static final LocalDateTimeToStringTypeConverter INSTANCE = new LocalDateTimeToStringTypeConverter();

    @Override
    public String convert(TypeMappingContext<LocalDateTime, String> context) {
        LocalDateTime value = context.getSource();

        if (value == null) {
            return null;
        }

        return JavaTimeUtils.formatAsDateTimeOffsetISO8601(value);
    }
}
