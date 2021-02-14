package com.github.stephenenright.typemapper.internal.util;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public abstract class ProxyUtils {

    private static Class<?> JAVASSIST_PROXY_FACTORY_CLASS;
    private static Method JAVASSIST_IS_PROXY_CLASS_METHOD;

    static {
        try {
            JAVASSIST_PROXY_FACTORY_CLASS = ProxyUtils.class.getClassLoader()
                    .loadClass("javassist.util.proxy.ProxyFactory");
            JAVASSIST_IS_PROXY_CLASS_METHOD = JAVASSIST_PROXY_FACTORY_CLASS.getMethod("isProxyClass",
                    new Class<?>[] { Class.class });
        } catch (Exception ignore) {
            // ignore
        }
    }

    private ProxyUtils() {

    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> unProxy(Class<?> type) {
        if (type.isInterface()) {
            return (Class<T>) type;
        }

        if (isProxy(type)) {
            final Class<?> superclass = type.getSuperclass();
            if (!superclass.equals(Object.class) && !superclass.equals(Proxy.class)) {
                return (Class<T>) superclass;
            }

            else {
                Class<?>[] interfaces = type.getInterfaces();
                if (interfaces.length > 0) {
                    return (Class<T>) interfaces[0];
                }
            }
        }

        return (Class<T>) type;
    }

    public static boolean isProxy(Class<?> type) {
        if (type.getName().contains("$HibernateProxy$")) {
            return true;
        } else if (type.getName().contains("$$EnhancerBy")) {
            return true;
        } else if (type.getName().contains("$ByteBuddy$")) {
            return true;
        } else if (Proxy.isProxyClass(type)) {
            return true;
        } else {
            return isJavassistProxy(type);
        }
    }

    private static boolean isJavassistProxy(Class<?> type) {
        try {
            return JAVASSIST_IS_PROXY_CLASS_METHOD != null
                    && (Boolean) JAVASSIST_IS_PROXY_CLASS_METHOD.invoke(null, type);
        } catch (Exception ignore) {
        }
        return false;
    }

}
