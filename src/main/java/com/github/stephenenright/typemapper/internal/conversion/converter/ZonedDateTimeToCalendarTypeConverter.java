package com.github.stephenenright.typemapper.internal.conversion.converter;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;

public class ZonedDateTimeToCalendarTypeConverter implements TypeConverter<ZonedDateTime, Calendar> {

    public static final ZonedDateTimeToCalendarTypeConverter INSTANCE = new ZonedDateTimeToCalendarTypeConverter();

    @Override
    public Calendar convert(TypeMappingContext<ZonedDateTime, Calendar> context) {
        ZonedDateTime value = context.getSource();

        if (value == null) {
            return null;
        }

        return GregorianCalendar.from(value);
    }
}
