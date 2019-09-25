package com.stephenenright.typemapper.internal.util;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public abstract class JavaBeanUtils {

    private JavaBeanUtils() {

    }

    public static boolean hasMethodGetterNamingConvention(Method method) {
        if (method == null) {
            return false;
        }

        return isMethodNameForGetter(method.getName());

    }

    public static boolean isMethodNameForGetter(String methodName) {
        return isMethodNameStandardGetter(methodName) || isMethodNameBooleanGetter(methodName);
    }

    public static boolean isMethodNameStandardGetter(String methodName) {
        return methodName.startsWith("get") && methodName.length() > 3;
    }

    public static boolean isMethodNameBooleanGetter(String methodName) {
        return methodName.startsWith("is") && methodName.length() > 2;
    }

    public static boolean hasMethodSetterNamingConvention(Method method) {
        if (method == null) {
            return false;
        }

        return isMethodNameStandardSetter(method.getName());
    }

    public static boolean isMethodNameStandardSetter(String methodName) {
        return methodName.startsWith("set") && methodName.length() > 3;
    }

    public static String extractPropertyNameFromGetter(Method method) {
        AssertUtils.notNull(method);

        final String methodName = method.getName();

        if (isMethodNameStandardGetter(methodName)) {
            return StringUtils.uncapitalize(methodName.substring(3));
        } else if (isMethodNameBooleanGetter(methodName)) {
            return StringUtils.uncapitalize(methodName.substring(2));
        } else {
            throw new IllegalStateException("Method name is not a valid getter");
        }
    }

    public static String extractPropertyNameFromSetter(Method method) {
        AssertUtils.notNull(method);

        final String methodName = method.getName();

        if (isMethodNameStandardSetter(methodName)) {
            return StringUtils.uncapitalize(methodName.substring(3));
        } else {
            throw new IllegalStateException("Method name is not a valid getter");
        }
    }

}
