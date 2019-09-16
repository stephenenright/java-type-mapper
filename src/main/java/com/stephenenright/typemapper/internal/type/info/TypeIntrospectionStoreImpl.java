package com.stephenenright.typemapper.internal.type.info;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.stephenenright.typemapper.TypeIntrospectionStore;
import com.stephenenright.typemapper.internal.collection.ConcurrentReferenceHashMap;

public class TypeIntrospectionStoreImpl implements TypeIntrospectionStore {

    private static final Method[] EMPTY_METHOD_ARRAY = new Method[0];

    private static final Map<Class<?>, Method[]> methodsCache = new ConcurrentReferenceHashMap<>(256);

    @Override
    public Method[] getDeclaredMethods(Class<?> clazz) {
        Method[] result = methodsCache.get(clazz);
        if (result == null) {
            try {
                Method[] declaredMethods = clazz.getDeclaredMethods();
                List<Method> defaultMethods = findMethodsForClass(clazz);
                if (defaultMethods != null) {
                    result = new Method[declaredMethods.length + defaultMethods.size()];
                    System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);
                    int index = declaredMethods.length;
                    for (Method defaultMethod : defaultMethods) {
                        result[index] = defaultMethod;
                        index++;
                    }
                } else {
                    result = declaredMethods;
                }
                methodsCache.put(clazz, (result.length == 0 ? EMPTY_METHOD_ARRAY : result));
            } catch (Throwable ex) {
                throw new IllegalStateException("Unable to introspect Class: " + clazz.getName() + " from ClassLoader: "
                        + clazz.getClassLoader(), ex);
            }
        }
        return result;
    }

    private static List<Method> findMethodsForClass(Class<?> cls) {
        List<Method> result = null;
        for (Class<?> ifc : cls.getInterfaces()) {
            for (Method ifcMethod : ifc.getMethods()) {
                if (!Modifier.isAbstract(ifcMethod.getModifiers())) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(ifcMethod);
                }
            }
        }
        return result;
    }

}
