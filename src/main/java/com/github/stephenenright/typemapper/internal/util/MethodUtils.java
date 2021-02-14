package com.github.stephenenright.typemapper.internal.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public abstract class MethodUtils {

    private MethodUtils() {}
    
    
    private static final Map<String, Method> OBJECT_METHOD_CACHE = new HashMap<String, Method>();

    static {
        for (Method method : Object.class.getDeclaredMethods()) {
            OBJECT_METHOD_CACHE.put(method.getName(), method);
        }
    }

    public static boolean isDefaultMethod(Method m) {
        return SystemUtils.JAVA_VERSION >= 1.8 && m.isDefault();
    }

    public static Method getObjectMethodByName(final String methodName) {
        return OBJECT_METHOD_CACHE.get(methodName);
    }

    public static boolean isAutoBoxingMethod(Method method) {
        Class<?>[] parameters = method.getParameterTypes();
        return method.getName().equals("valueOf") && parameters.length == 1 && parameters[0].isPrimitive()
                && ClassUtils.resolvePrimitiveAsWrapperIfNessecary(parameters[0]).equals(method.getDeclaringClass());
    }

    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers())
                || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))) {
            method.setAccessible(true);
        }
    }

}
