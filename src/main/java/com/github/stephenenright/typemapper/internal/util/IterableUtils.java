package com.github.stephenenright.typemapper.internal.util;

import java.util.Collection;

public abstract class IterableUtils {

    private IterableUtils() {

    }

    public static boolean isIterable(Class<?> type) {
        return type.isArray() || Collection.class.isAssignableFrom(type);
    }

}
