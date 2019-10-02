package com.stephenenright.typemapper.internal.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public abstract class ClassUtils {

    private static final Map<Class<?>, Class<?>> primitiveWrapperToPrimitiveTypeMap = new IdentityHashMap<>(8);
    private static final Map<Class<?>, Class<?>> primitiveToWrapperTypeMap = new IdentityHashMap<>(8);
    private static final Map<Class<?>, Object> defaultValue;

    static {
        primitiveWrapperToPrimitiveTypeMap.put(Boolean.class, boolean.class);
        primitiveWrapperToPrimitiveTypeMap.put(Byte.class, byte.class);
        primitiveWrapperToPrimitiveTypeMap.put(Character.class, char.class);
        primitiveWrapperToPrimitiveTypeMap.put(Double.class, double.class);
        primitiveWrapperToPrimitiveTypeMap.put(Float.class, float.class);
        primitiveWrapperToPrimitiveTypeMap.put(Integer.class, int.class);
        primitiveWrapperToPrimitiveTypeMap.put(Long.class, long.class);
        primitiveWrapperToPrimitiveTypeMap.put(Short.class, short.class);
        primitiveWrapperToPrimitiveTypeMap.put(Void.class, void.class);

        primitiveWrapperToPrimitiveTypeMap.forEach((key, value) -> {
            primitiveToWrapperTypeMap.put(value, key);
        });

        defaultValue = new HashMap<Class<?>, Object>();
        defaultValue.put(Boolean.TYPE, Boolean.FALSE);
        defaultValue.put(Byte.TYPE, Byte.valueOf((byte) 0));
        defaultValue.put(Short.TYPE, Short.valueOf((short) 0));
        defaultValue.put(Integer.TYPE, Integer.valueOf(0));
        defaultValue.put(Long.TYPE, Long.valueOf(0L));
        defaultValue.put(Float.TYPE, Float.valueOf(0.0f));
        defaultValue.put(Double.TYPE, Double.valueOf(0.0d));
        defaultValue.put(Character.TYPE, Character.valueOf('\u0000'));

    }

    private ClassUtils() {

    }

    public static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || isPrimitiveWrapper(clazz);
    }

    public static boolean isNotPrimitive(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            if (isPrimitive(clazz)) {
                return false;
            }
        }

        return true;

    }

    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        return primitiveToWrapperTypeMap.containsKey(clazz);

    }

    public static Class<?> resolvePrimitiveAsWrapperIfNessecary(Class<?> clazz) {
        AssertUtils.notNull(clazz, "Class is null");

        if (!clazz.isPrimitive()) {
            return clazz;
        }

        return primitiveToWrapperTypeMap.get(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getPrimitiveDefaultValue(Class<?> type) {
        return type.isPrimitive() ? (T) defaultValue.get(type) : null;
    }

    public static boolean isAssignable(Class<?> leftClass, Class<?> rightClass) {
        AssertUtils.notNull(leftClass, "Left class is null");
        AssertUtils.notNull(rightClass, "Right class is null");
        if (leftClass.isAssignableFrom(rightClass)) {
            return true;
        }
        if (leftClass.isPrimitive()) {
            Class<?> resolvedPrimitive = primitiveWrapperToPrimitiveTypeMap.get(rightClass);
            if (leftClass == resolvedPrimitive) {
                return true;
            }
        } else {
            Class<?> resolvedWrapper = primitiveToWrapperTypeMap.get(rightClass);
            if (resolvedWrapper != null && leftClass.isAssignableFrom(resolvedWrapper)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isCollection(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }

        return Collection.class.isAssignableFrom(clazz);
    }

    public static boolean isArray(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        return clazz.isArray();
    }

    public static boolean isMap(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }

        return Map.class.isAssignableFrom(clazz);
    }
}
