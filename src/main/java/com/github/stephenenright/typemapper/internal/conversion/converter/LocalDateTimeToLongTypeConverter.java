package com.github.stephenenright.typemapper.internal.conversion.converter;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;

public class LocalDateTimeToLongTypeConverter implements TypeConverter<LocalDateTime, Long> {

    public static final LocalDateTimeToLongTypeConverter INSTANCE = new LocalDateTimeToLongTypeConverter();

    @Override
    public Long convert(TypeMappingContext<LocalDateTime, Long> context) {
        LocalDateTime value = context.getSource();

        if (value == null) {
            return null;
        }

        return value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
