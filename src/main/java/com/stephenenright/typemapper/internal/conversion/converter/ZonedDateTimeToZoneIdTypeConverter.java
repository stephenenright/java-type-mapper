package com.stephenenright.typemapper.internal.conversion.converter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;

public class ZonedDateTimeToZoneIdTypeConverter implements TypeConverter<ZonedDateTime, ZoneId> {

    public static final ZonedDateTimeToZoneIdTypeConverter INSTANCE = new ZonedDateTimeToZoneIdTypeConverter();

    @Override
    public ZoneId convert(TypeMappingContext<ZonedDateTime, ZoneId> context) {
        ZonedDateTime value = context.getSource();

        if (value == null) {
            return null;
        }

        return value.getZone();
    }
}
