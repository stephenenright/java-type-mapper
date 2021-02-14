package com.github.stephenenright.typemapper.internal.conversion.converter;

import java.time.LocalDate;
import java.time.ZoneId;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;

public class LocalDateToLongTypeConverter implements TypeConverter<LocalDate, Long> {

    public static final LocalDateToLongTypeConverter INSTANCE = new LocalDateToLongTypeConverter();

    @Override
    public Long convert(TypeMappingContext<LocalDate, Long> context) {
        LocalDate value = context.getSource();

        if (value == null) {
            return null;
        }

        return value.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
