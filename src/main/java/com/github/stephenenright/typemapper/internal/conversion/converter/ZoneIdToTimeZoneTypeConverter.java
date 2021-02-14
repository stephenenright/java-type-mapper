package com.github.stephenenright.typemapper.internal.conversion.converter;

import java.time.ZoneId;
import java.util.TimeZone;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;

public class ZoneIdToTimeZoneTypeConverter implements TypeConverter<ZoneId, TimeZone> {

    public static final ZoneIdToTimeZoneTypeConverter INSTANCE = new ZoneIdToTimeZoneTypeConverter();

    @Override
    public TimeZone convert(TypeMappingContext<ZoneId, TimeZone> context) {
        ZoneId value = context.getSource();

        if (value == null) {
            return null;
        }

        return TimeZone.getTimeZone(value);
    }
}
