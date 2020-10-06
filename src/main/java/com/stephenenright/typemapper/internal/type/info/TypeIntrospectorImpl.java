package com.stephenenright.typemapper.internal.type.info;

import com.stephenenright.typemapper.TypeIntrospector;
import com.stephenenright.typemapper.internal.collection.ConcurrentReferenceHashMap;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeIntrospectorImpl implements TypeIntrospector {

    private static final Method[] EMPTY_METHOD_ARRAY = new Method[0];

    private static final Map<Class<?>, Method[]> methodsCache = new ConcurrentReferenceHashMap<>(256);

    @Override
    public Method[] getDeclaredMethods(Class<?> clazz) {
        final List<Method> methods = new ArrayList<>(32);
        doWithMethods(clazz, methods);
        return methods.toArray(EMPTY_METHOD_ARRAY);
    }

    private void doWithMethods(Class<?> clazz, List<Method> methodsList) {
        // Keep backing up the inheritance hierarchy.
        Method[] methods = getDeclaredMethodsInternal(clazz);
        for (Method method : methods) {
            methodsList.add(method);
        }
        if (clazz.getSuperclass() != null && (clazz.getSuperclass() != Object.class)) {
            doWithMethods(clazz.getSuperclass(), methodsList);
        } else if (clazz.isInterface()) {
            for (Class<?> superIfc : clazz.getInterfaces()) {
                doWithMethods(superIfc, methodsList);
            }
        }
    }


    private static Method[] getDeclaredMethodsInternal(Class<?> clazz) {
        Method[] result = methodsCache.get(clazz);
        if (result == null) {
            try {
                Method[] declaredMethods = clazz.getDeclaredMethods();
                List<Method> defaultMethods = findConcreteMethodsOnInterfaces(clazz);
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

    private static List<Method> findConcreteMethodsOnInterfaces(Class<?> clazz) {
        List<Method> result = null;
        for (Class<?> ifc : clazz.getInterfaces()) {
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
