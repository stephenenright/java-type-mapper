package com.github.stephenenright.typemapper;

import java.lang.reflect.Method;

public interface TypeIntrospector {

    public Method[] getDeclaredMethods(Class<?> clazz);

}
