package com.stephenenright.typemapper.internal.util;

import java.util.TimeZone;

public abstract class StringUtils {

    private StringUtils() {

    }

    public static boolean isNullOrEmpty(String str) {
        if (str == null || str.trim().equals("")) {
            return true;
        }

        return false;
    }

    public static String trimAll(String str) {
        if (isNullOrEmpty(str) || str.length() == 0) {
            return str;
        }

        int strLen = str.length();
        StringBuilder builder = new StringBuilder(str.length());

        for (int i = 0; i < strLen; i++) {
            char c = str.charAt(i);

            if (!Character.isWhitespace(c)) {
                builder.append(c);
            }
        }

        return builder.toString();
    }

    public static TimeZone timezoneFromString(String timeZoneString) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
        if ("GMT".equals(timeZone.getID()) && !timeZoneString.startsWith("GMT")) {
            throw new IllegalArgumentException("Time zone not valid: " + timeZoneString);
        }
        return timeZone;
    }

    public static String uncapitalize(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }

        char chars[] = str.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return String.valueOf(chars);

    }
}
