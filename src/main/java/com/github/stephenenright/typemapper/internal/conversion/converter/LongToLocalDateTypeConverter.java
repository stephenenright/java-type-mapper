package com.github.stephenenright.typemapper.internal.conversion.converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;

public class LongToLocalDateTypeConverter implements TypeConverter<Long, LocalDate> {

    public static final LongToLocalDateTypeConverter INSTANCE = new LongToLocalDateTypeConverter();

    @Override
    public LocalDate convert(TypeMappingContext<Long, LocalDate> context) {
        Long value = context.getSource();

        if (value == null) {
            return null;
        }

        Instant instant = Instant.ofEpochMilli(value);
        return LocalDate.ofInstant(instant, ZoneId.systemDefault());
    }
}
