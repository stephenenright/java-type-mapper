package com.stephenenright.typemapper;

import java.lang.reflect.Method;

public interface TypeIntrospectionStore {

    public Method[] getDeclaredMethods(Class<?> clazz);

}
