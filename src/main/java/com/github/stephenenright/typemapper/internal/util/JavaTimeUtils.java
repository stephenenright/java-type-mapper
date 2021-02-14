package com.github.stephenenright.typemapper.internal.util;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public abstract class JavaTimeUtils {

    private static final DateTimeFormatter FORMATTER_ISO8601_DATE_TIME_OFFSET;
    private static final DateTimeFormatter FORMATTER_ISO8601_DATE_OFFSET;

    static {
        FORMATTER_ISO8601_DATE_TIME_OFFSET = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        FORMATTER_ISO8601_DATE_OFFSET = DateTimeFormatter.ISO_OFFSET_DATE;
    }

    private JavaTimeUtils() {

    }

    public static String formatAsDateTimeOffsetISO8601(TemporalAccessor accessor) {
        if (accessor == null) {
            return null;
        }

        return FORMATTER_ISO8601_DATE_TIME_OFFSET.format(accessor);
    }

    public static String formatAsDateOffsetISO8601(TemporalAccessor accessor) {
        if (accessor == null) {
            return null;
        }

        return FORMATTER_ISO8601_DATE_OFFSET.format(accessor);
    }
}
