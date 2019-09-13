package com.stephenenright.typemapper.internal.util;

public abstract class AssertUtils {

    private AssertUtils() {

    }

    public static void notNull(Object value) {
        notNull(value, "Assertion failed object is null");
    }

    public static void notNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException("Assertion failed condition is not true");
        }
    }

}
