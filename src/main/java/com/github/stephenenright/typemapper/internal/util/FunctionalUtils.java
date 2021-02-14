package com.github.stephenenright.typemapper.internal.util;

import java.util.function.Supplier;

public abstract class FunctionalUtils {

    private FunctionalUtils() {
    }

    public static <T> Supplier<T> supplierFor(T obj) {
        return new Supplier<T>() {

            @Override
            public T get() {
                return obj;
            }

        };
    }

}
