package com.stephenenright.typemapper.internal.conversion.converter.factory;

import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConverterFactory;
import com.stephenenright.typemapper.internal.conversion.util.DateTypeConversionUtils;

public class ObjectToDateTypeConverterFactory implements TypeConverterFactory<Object, Date> {

    public static final ObjectToDateTypeConverterFactory INSTANCE = new ObjectToDateTypeConverterFactory();
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Date> TypeConverter<Object, T> getTypeConverter(TypeMappingContext<?, ?> context) {
        Object source = context.getSource();

        if (source == null) {
            return null;
        }

        if (source instanceof Long) {
            return (TypeConverter<Object, T>) LongToDateTypeConverter.INSTANCE;
        } else if (source instanceof String) {
            return (TypeConverter<Object, T>) StringToDateTypeConverter.INSTANCE;
        } else if (source instanceof Calendar) {
            return (TypeConverter<Object, T>) CalendarToDateTypeConverter.INSTANCE;
        } else if (source instanceof XMLGregorianCalendar) {
            return (TypeConverter<Object, T>) XMLGregorianCalendarToDateTypeConverter.INSTANCE;
        } else if (source instanceof Date) {
            return (TypeConverter<Object, T>) DateToDateTypeConverter.INSTANCE;
        } else {
            return null;
        }

    }

    public static final class DateToDateTypeConverter implements TypeConverter<Object, Date> {

        public static final DateToDateTypeConverter INSTANCE = new DateToDateTypeConverter();

        @Override
        public Date convert(TypeMappingContext<Object, Date> context) {
            Object sourceObject = context.getSource();

            if (sourceObject == null) {
                return null;
            }

            Date source = (Date) sourceObject;
            return DateTypeConversionUtils.dateFromLong(((Date) source).getTime(), Date.class);

        }

    }

    public static final class StringToDateTypeConverter implements TypeConverter<Object, Date> {

        public static final StringToDateTypeConverter INSTANCE = new StringToDateTypeConverter();

        @Override
        public Date convert(TypeMappingContext<Object, Date> context) {
            Object sourceObject = context.getSource();

            if (sourceObject == null) {
                return null;
            }

            String source = (String) sourceObject;
            return DateTypeConversionUtils.dateFromString(source, Date.class);

        }

    }

    public static final class LongToDateTypeConverter implements TypeConverter<Object, Date> {

        public static final LongToDateTypeConverter INSTANCE = new LongToDateTypeConverter();

        @Override
        public Date convert(TypeMappingContext<Object, Date> context) {
            Object sourceObject = context.getSource();

            if (sourceObject == null) {
                return null;
            }

            Long source = (Long) sourceObject;
            return DateTypeConversionUtils.dateFromLong(source, Date.class);

        }

    }

    public static final class CalendarToDateTypeConverter implements TypeConverter<Object, Date> {

        public static final CalendarToDateTypeConverter INSTANCE = new CalendarToDateTypeConverter();

        @Override
        public Date convert(TypeMappingContext<Object, Date> context) {
            Object sourceObject = context.getSource();

            if (sourceObject == null) {
                return null;
            }

            Calendar source = (Calendar) sourceObject;
            return DateTypeConversionUtils.dateFromLong(((Calendar) source).getTimeInMillis(), Date.class);

        }
    }

    public static final class XMLGregorianCalendarToDateTypeConverter implements TypeConverter<Object, Date> {

        public static final XMLGregorianCalendarToDateTypeConverter INSTANCE = new XMLGregorianCalendarToDateTypeConverter();

        @Override
        public Date convert(TypeMappingContext<Object, Date> context) {
            Object sourceObject = context.getSource();

            if (sourceObject == null) {
                return null;
            }

            XMLGregorianCalendar source = (XMLGregorianCalendar) sourceObject;
            return DateTypeConversionUtils.dateFromLong(source.toGregorianCalendar().getTimeInMillis(), Date.class);

        }

    }

}
