package com.github.stephenenright.typemapper.internal.util;

import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import com.github.stephenenright.typemapper.internal.common.CommonConstants;

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

    public static String joinStrings(String sep, String... strs) {
        if (strs.length == 0) {
            return CommonConstants.EMPTY_STRING;
        }

        StringBuilder builder = new StringBuilder();
        for (String str : strs) {
            if (isNullOrEmpty(str)) {
                continue;
            }

            if (builder.length() > 0) {
                builder.append(sep);
            }

            builder.append(str);
        }

        return builder.toString();
    }

    public static String joinStrings(String sep, List<String> strs) {
        if (strs.size() == 0) {
            return CommonConstants.EMPTY_STRING;
        }

        StringBuilder builder = new StringBuilder();
        for (String str : strs) {
            if (isNullOrEmpty(str)) {
                continue;
            }

            if (builder.length() > 0) {
                builder.append(sep);
            }

            builder.append(str);
        }

        return builder.toString();
    }
    
    
    public static String joinStrings(String sep, List<String> strs, Set<Integer> indexesToExclude) {
        if (strs.size() == 0) {
            return CommonConstants.EMPTY_STRING;
        }

        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (String str : strs) {
            if(indexesToExclude.contains(i)) {
                i++;
                continue;
            }
            
            if (isNullOrEmpty(str)) {
                continue;
            }

            if (builder.length() > 0) {
                builder.append(sep);
            }

            builder.append(str);
            
            
            i++;
            
        }

        return builder.toString();
    }
    
    
    
    
    

    public static String joinStrings(String sep, List<String> strs, int toIndex) {
        if (strs.size() == 0) {
            return CommonConstants.EMPTY_STRING;
        }

        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (String str : strs) {
            if (i >= toIndex) {
                break;
            }

            if (isNullOrEmpty(str)) {
                continue;
            }

            if (builder.length() > 0) {
                builder.append(sep);
            }

            builder.append(str);

            i++;
        }

        return builder.toString();
    }

}
