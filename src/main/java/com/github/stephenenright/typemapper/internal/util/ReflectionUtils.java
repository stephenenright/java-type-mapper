package com.github.stephenenright.typemapper.internal.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ReflectionUtils {

    private ReflectionUtils() {

    }

    public static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter. Superclass is instance of: " + superclass.getClass());
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments()[0];
    }

    public static Type[] getGenericInterfaceTypeParameters(Class<?> cls) {
        Type[] types = cls.getGenericInterfaces();

        if (types.length == 0) {
            throw new RuntimeException("Missing type parameters. Class is instance of: " + cls.getClass());
        }

        Type genericType = types[0];

        if (!(genericType instanceof ParameterizedType)) {
            throw new RuntimeException("Missing type parameter. Type is: " + genericType);
        }

        ParameterizedType parameterized = (ParameterizedType) genericType;
        return parameterized.getActualTypeArguments();
    }

    public static void makeAccessible(Constructor<?> constructor) {
        if ((!Modifier.isPublic(constructor.getModifiers())
                || !Modifier.isPublic(constructor.getDeclaringClass().getModifiers())) && !constructor.isAccessible()) {
            constructor.setAccessible(true);
        }
    }

}
