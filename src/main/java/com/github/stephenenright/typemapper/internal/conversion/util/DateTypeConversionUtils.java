package com.github.stephenenright.typemapper.internal.conversion.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.github.stephenenright.typemapper.internal.util.StringUtils;

public abstract class DateTypeConversionUtils {

    private DateTypeConversionUtils() {

    }

    public static Object dateToObject(Date source, Class<?> destinationType) {
        if (source == null) {
            return null;
        }

        if (destinationType.equals(Long.class)) {
            return source.getTime();
        } else if (destinationType.equals(String.class)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.format(source);
        } else if (destinationType.equals(Calendar.class)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(source);
            return calendar;
        } else if (destinationType.equals(Timestamp.class)) {
            return new Timestamp(source.getTime());
        } else if (destinationType.equals(Time.class)) {
            return new Time(source.getTime());
        } else if (destinationType.equals(java.sql.Date.class)) {
            return new java.sql.Date(source.getTime());
        } else if (destinationType.equals(Date.class)) {
            return new Date(source.getTime());
        } else {
            return null; // TODO THROW ERROR
        }
    }

    public static Date dateFromLong(Long source, Class<?> destinationType) {
        if (source == null) {
            return null;
        }

        if (destinationType.equals(Date.class)) {
            return new Date(source);
        } else if (destinationType.equals(Time.class)) {
            return new Time(source);
        } else if (destinationType.equals(java.sql.Date.class)) {
            return new java.sql.Date(source);
        } else if (destinationType.equals(Timestamp.class)) {
            return new Timestamp(source);
        } else {
            return null; // TODO THROW ERROR
        }

    }

    public static Date dateFromString(String source, Class<?> destinationType) {
        if (StringUtils.isNullOrEmpty(source)) {
            return null;
        }

        final String sanitized = source.trim();

        if (destinationType.equals(Timestamp.class)) {
            try {
                return Timestamp.valueOf(source);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("String must have format yyyy-MM-dd");
            }
        } else if (destinationType.equals(java.sql.Date.class)) {
            try {
                return java.sql.Date.valueOf(sanitized);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("String must have format yyyy-MM-dd");
            }
        } else if (destinationType.equals(Time.class)) {
            try {
                return Time.valueOf(sanitized);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("String must have format HH:mm:ss");
            }
        } else if (destinationType.equals(java.util.Date.class)) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return simpleDateFormat.parse(source);
            } catch (ParseException e) {
                throw new RuntimeException("String must have format yyyy-MM-dd");
            }
        } else {
            return null; // TODO THROW ERROR;
        }

    }

}
