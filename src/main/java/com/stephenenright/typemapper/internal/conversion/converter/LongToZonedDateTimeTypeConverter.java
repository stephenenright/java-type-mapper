package com.stephenenright.typemapper.internal.conversion.converter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;

public class LongToZonedDateTimeTypeConverter implements TypeConverter<Long, ZonedDateTime> {

    @Override
    public  ZonedDateTime convert(TypeMappingContext<Long,  ZonedDateTime> context) {
        Long value = context.getSource();

        if (value == null) {
            return null;
        }
        
        Instant instant = Instant.ofEpochMilli(value);
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
