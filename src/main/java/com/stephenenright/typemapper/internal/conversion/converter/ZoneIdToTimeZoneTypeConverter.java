package com.stephenenright.typemapper.internal.conversion.converter;

import java.time.ZoneId;
import java.util.TimeZone;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;

public class ZoneIdToTimeZoneTypeConverter implements TypeConverter<ZoneId, TimeZone> {

    @Override
    public TimeZone convert(TypeMappingContext<ZoneId, TimeZone> context) {
        ZoneId value = context.getSource();

        if (value == null) {
            return null;
        }
        
        return TimeZone.getTimeZone(value);
    }
}
