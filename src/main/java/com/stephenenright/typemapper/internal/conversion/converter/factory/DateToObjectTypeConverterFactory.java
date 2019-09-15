package com.stephenenright.typemapper.internal.conversion.converter.factory;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConverterFactory;
import com.stephenenright.typemapper.internal.conversion.util.DateTypeConversionUtils;
import com.stephenenright.typemapper.internal.util.AssertUtils;
import com.stephenenright.typemapper.internal.util.ClassUtils;

public class DateToObjectTypeConverterFactory implements TypeConverterFactory<Date, Object> {

    public static final DateToObjectTypeConverterFactory INSTANCE = new DateToObjectTypeConverterFactory();

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeConverter<Date, T> getTypeConverter(TypeMappingContext<?, ?> context) {
        Class<?> destinationType = ClassUtils.resolvePrimitiveAsWrapperIfNessecary(context.getDestinationType());
        AssertUtils.notNull(destinationType, "Target type must not be null");
        
        if (Long.class == destinationType) {
            return (TypeConverter<Date, T>) DateToLongTypeConverter.INSTANCE;
        }
        else if (String.class == destinationType) {
            return (TypeConverter<Date, T>) DateToStringTypeConverter.INSTANCE;
        }
        else if (Calendar.class == destinationType) {
            return (TypeConverter<Date, T>) DateToCalendarTypeConverter.INSTANCE;
        }
        else if (Timestamp.class == destinationType) {
            return (TypeConverter<Date, T>) DateToTimestampTypeConverter.INSTANCE;
        }
        else if (Time.class == destinationType) {
            return (TypeConverter<Date, T>) DateToTimeTypeConverter.INSTANCE;
        }
        else if (java.sql.Date.class == destinationType) {
            return (TypeConverter<Date, T>) DateToSqlDateTypeConverter.INSTANCE;
        }

        return null;
    }
    
    public static final class DateToDateTypeConverter implements TypeConverter<Date, Object> {

        public static final DateToDateTypeConverter INSTANCE = new DateToDateTypeConverter();

        @Override
        public Object convert(TypeMappingContext<Date, Object> context) {
            Date source = context.getSource();
            
            if(source==null) {
                return null;
            }
            
            return DateTypeConversionUtils.dateToObject(source,Date.class);

        }
    }
    
    public static final class DateToLongTypeConverter implements TypeConverter<Date, Object> {

        public static final DateToLongTypeConverter INSTANCE = new DateToLongTypeConverter();

        @Override
        public Object convert(TypeMappingContext<Date, Object> context) {
            Date source = context.getSource();
            
            if(source==null) {
                return null;
            }
            
            
            return DateTypeConversionUtils.dateToObject(source,Long.class);
        }
    }
    
    
    public static final class DateToStringTypeConverter implements TypeConverter<Date, Object> {

        public static final DateToStringTypeConverter INSTANCE = new DateToStringTypeConverter();

        @Override
        public Object convert(TypeMappingContext<Date, Object> context) {
            Date source = context.getSource();
            
            if(source==null) {
                return null;
            }
            
            return DateTypeConversionUtils.dateToObject(source,String.class);
        }
    }
    
    
    public static final class DateToCalendarTypeConverter implements TypeConverter<Date, Object> {

        public static final DateToCalendarTypeConverter INSTANCE = new DateToCalendarTypeConverter();

        @Override
        public Object convert(TypeMappingContext<Date, Object> context) {
            Date source = context.getSource();
            
            if(source==null) {
                return null;
            }
            
            return DateTypeConversionUtils.dateToObject(source,Calendar.class);
        }
    }
    
    public static final class DateToTimestampTypeConverter implements TypeConverter<Date, Object> {

        public static final DateToTimestampTypeConverter INSTANCE = new DateToTimestampTypeConverter();

        @Override
        public Object convert(TypeMappingContext<Date, Object> context) {
            Date source = context.getSource();
            
            if(source==null) {
                return null;
            }
            
            return DateTypeConversionUtils.dateToObject(source,Timestamp.class);
        }
    }
    
    
    public static final class DateToTimeTypeConverter implements TypeConverter<Date, Object> {

        public static final DateToTimeTypeConverter INSTANCE = new DateToTimeTypeConverter();

        @Override
        public Object convert(TypeMappingContext<Date, Object> context) {
            Date source = context.getSource();
            
            if(source==null) {
                return null;
            }
            
            return DateTypeConversionUtils.dateToObject(source,Time.class);
        }
    }
    
    
    public static final class DateToSqlDateTypeConverter implements TypeConverter<Date, Object> {

        public static final DateToSqlDateTypeConverter INSTANCE = new DateToSqlDateTypeConverter();

        @Override
        public Object convert(TypeMappingContext<Date, Object> context) {
            Date source = context.getSource();
            
            if(source==null) {
                return null;
            }
            
            return DateTypeConversionUtils.dateToObject(source,java.sql.Date.class);
        }
    }
    
    
    
}
