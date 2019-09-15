package com.stephenenright.typemapper.internal.conversion;

import com.stephenenright.typemapper.internal.conversion.converter.EnumToStringTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.LocalDateTimeToLongTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.LocalDateTimeToStringTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.LocalDateToLongTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.LocalDateToStringTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.LongToLocalDateTimeTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.LongToLocalDateTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.LongToZonedDateTimeTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.NumberToCharacterTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.NumberToEnumTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.ObjectToStringTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.StringToBooleanTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.StringToCharacterTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.StringToCharsetTypeConveter;
import com.stephenenright.typemapper.internal.conversion.converter.StringToEnumTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.StringToPropertiesTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.StringToTimeZoneTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.StringToUUIDTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.ZoneIdToTimeZoneTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.ZonedDateTimeToCalendarTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.ZonedDateTimeToLongTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.ZonedDateTimeToStringTypeConverter;
import com.stephenenright.typemapper.internal.conversion.converter.factory.CharacterToNumberTypeConverterFactory;
import com.stephenenright.typemapper.internal.conversion.converter.factory.EnumToNumberTypeConverterFactory;
import com.stephenenright.typemapper.internal.conversion.converter.factory.NumberToNumberTypeConverterFactory;
import com.stephenenright.typemapper.internal.conversion.converter.factory.ObjectToDateTypeConverterFactory;
import com.stephenenright.typemapper.internal.conversion.converter.factory.StringToNumberTypeConverterFactory;

public class TypeConverterCollectionDefaultImpl extends TypeConverterCollectionImpl {

    public TypeConverterCollectionDefaultImpl() {
        addDefaultConverters();
        addDefaultConverterFactories();
    }

    private void addDefaultConverters() {
        add(EnumToStringTypeConverter.INSTANCE);
        add(LocalDateTimeToLongTypeConverter.INSTANCE);
        add(LocalDateTimeToStringTypeConverter.INSTANCE);
        add(LocalDateToLongTypeConverter.INSTANCE);
        add(LocalDateToStringTypeConverter.INSTANCE);
        add(LongToLocalDateTimeTypeConverter.INSTANCE);
        add(LongToLocalDateTypeConverter.INSTANCE);
        add(LongToZonedDateTimeTypeConverter.INSTANCE);
        add(NumberToCharacterTypeConverter.INSTANCE);
        add(NumberToEnumTypeConverter.INSTANCE);
        add(ObjectToStringTypeConverter.INSTANCE);
        add(StringToBooleanTypeConverter.INSTANCE);
        add(StringToCharacterTypeConverter.INSTANCE);
        add(StringToCharsetTypeConveter.INSTANCE);
        add(StringToEnumTypeConverter.INSTANCE);
        add(StringToPropertiesTypeConverter.INSTANCE);
        add(StringToTimeZoneTypeConverter.INSTANCE);
        add(StringToUUIDTypeConverter.INSTANCE);
        add(ZonedDateTimeToCalendarTypeConverter.INSTANCE);
        add(ZonedDateTimeToLongTypeConverter.INSTANCE);
        add(ZonedDateTimeToStringTypeConverter.INSTANCE);
        add(ZoneIdToTimeZoneTypeConverter.INSTANCE);

    }

    private void addDefaultConverterFactories() {
        add(CharacterToNumberTypeConverterFactory.INSTANCE);
        add(EnumToNumberTypeConverterFactory.INSTANCE);
        add(NumberToNumberTypeConverterFactory.INSTANCE);
        add(ObjectToDateTypeConverterFactory.INSTANCE);
        add(StringToNumberTypeConverterFactory.INSTANCE);
    }

}
