package com.github.stephenenright.typemapper.internal.conversion;

import com.github.stephenenright.typemapper.internal.conversion.converter.ArrayToArrayTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.ArrayToCollectionTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.CollectionToArrayTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.CollectionToCollectionTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.EnumToStringTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.LocalDateTimeToLongTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.LocalDateTimeToStringTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.LocalDateToLongTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.LocalDateToStringTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.LongToLocalDateTimeTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.LongToLocalDateTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.LongToZonedDateTimeTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.MapToMapConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.NumberToCharacterTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.NumberToEnumTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.ObjectToObjectConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.ObjectToStringTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.StringToBooleanTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.StringToCharacterTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.StringToCharsetTypeConveter;
import com.github.stephenenright.typemapper.internal.conversion.converter.StringToEnumTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.StringToPropertiesTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.StringToTimeZoneTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.StringToUUIDTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.ZoneIdToTimeZoneTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.ZonedDateTimeToCalendarTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.ZonedDateTimeToLongTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.ZonedDateTimeToStringTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.ZonedDateTimeToZoneIdTypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.factory.CharacterToNumberTypeConverterFactory;
import com.github.stephenenright.typemapper.internal.conversion.converter.factory.DateToObjectTypeConverterFactory;
import com.github.stephenenright.typemapper.internal.conversion.converter.factory.EnumToNumberTypeConverterFactory;
import com.github.stephenenright.typemapper.internal.conversion.converter.factory.NumberToNumberTypeConverterFactory;
import com.github.stephenenright.typemapper.internal.conversion.converter.factory.ObjectToDateTypeConverterFactory;
import com.github.stephenenright.typemapper.internal.conversion.converter.factory.StringToNumberTypeConverterFactory;

public class TypeConverterCollectionDefaultImpl extends TypeConverterCollectionImpl {

    public TypeConverterCollectionDefaultImpl() {
        addDefaultConverters();
        addDefaultConverterFactories();
    }

    private void addDefaultConverters() {
        add(ArrayToArrayTypeConverter.INSTANCE);
        add(ArrayToCollectionTypeConverter.INSTANCE);
        add(CollectionToArrayTypeConverter.INSTANCE);
        add(CollectionToCollectionTypeConverter.INSTANCE);
        add(EnumToStringTypeConverter.INSTANCE);
        add(LocalDateTimeToLongTypeConverter.INSTANCE);
        add(LocalDateTimeToStringTypeConverter.INSTANCE);
        add(LocalDateToLongTypeConverter.INSTANCE);
        add(LocalDateToStringTypeConverter.INSTANCE);
        add(LongToLocalDateTimeTypeConverter.INSTANCE);
        add(LongToLocalDateTypeConverter.INSTANCE);
        add(LongToZonedDateTimeTypeConverter.INSTANCE);
        add(NumberToCharacterTypeConverter.INSTANCE);
        add(MapToMapConverter.INSTANCE);
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
        add(ZonedDateTimeToZoneIdTypeConverter.INSTANCE);
        add(ZoneIdToTimeZoneTypeConverter.INSTANCE);
        add(ObjectToObjectConverter.INSTANCE);
    }

    private void addDefaultConverterFactories() {
        add(CharacterToNumberTypeConverterFactory.INSTANCE);
        add(DateToObjectTypeConverterFactory.INSTANCE);
        add(EnumToNumberTypeConverterFactory.INSTANCE);
        add(NumberToNumberTypeConverterFactory.INSTANCE);
        add(ObjectToDateTypeConverterFactory.INSTANCE);
        add(StringToNumberTypeConverterFactory.INSTANCE);
    }

}
