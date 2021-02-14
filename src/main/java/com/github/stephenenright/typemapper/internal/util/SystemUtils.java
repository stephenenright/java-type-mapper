package com.github.stephenenright.typemapper.internal.util;

public abstract class SystemUtils {

    public static final Double JAVA_VERSION;

    static {
        JAVA_VERSION = Double.parseDouble(System.getProperty("java.specification.version", "0"));
    }

}
