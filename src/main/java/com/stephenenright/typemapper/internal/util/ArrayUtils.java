package com.stephenenright.typemapper.internal.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;

import com.stephenenright.typemapper.TypeMappingContext;

public abstract class ArrayUtils {

    private ArrayUtils() {

    }

    public static boolean isArray(Class<?> type) {
        return type.isArray();

    }

    public static Object getArrayElementSafe(Object array, int index) {
        if (array == null) {
            return null;
        }

        try {
            return Array.get(array, index);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public static Class<?> getElementDestinationType(TypeMappingContext<? extends Object, Object> context) {
        if (context.getGenericDestinationType() instanceof GenericArrayType) {
            return TypeUtils.getRawType(context.getGenericDestinationType()).getComponentType();
        }

        return context.getDestinationType().getComponentType();
    }

    public static Object newInstance(Class<?> arrayType) {
        if (!arrayType.isArray()) {
            throw new IllegalArgumentException("Array type is not an array");
        }

        Class<?> componentType = arrayType.getComponentType();

        if (componentType.isArray()) {
            Object array = Array.newInstance(componentType, 1);
            Array.set(array, 0, Array.newInstance(componentType.getComponentType(), 0));
            return array;
        } else {
            return Array.newInstance(componentType, 0);
        }
    }

    public static Object newInstance(TypeMappingContext<?, ?> context) {
        final int destinationLength = context.getDestination() != null ? getLength(context.getDestination()) : 0;
        final int sourceLength = getLength(context.getSource());
        Object destinationObject = context.getDestination();
        Class<?> destType = context.getDestinationType();
        Object destination = Array.newInstance(destType.isArray() ? destType.getComponentType() : destType,
                Math.max(sourceLength, destinationLength));

        if (destinationObject != null) {
            System.arraycopy(destinationObject, 0, destination, 0, destinationLength);
        }

        return destination;
    }

    public static int getLength(Object array) {
        AssertUtils.isTrue(array.getClass().isArray());
        return Array.getLength(array);
    }
}
