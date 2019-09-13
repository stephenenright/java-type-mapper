package com.stephenenright.typemapper.internal.util;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;

public abstract class ClassUtils {

    private static final Map<Class<?>, Class<?>> primitiveWrapperToPrimitiveTypeMap = new IdentityHashMap<>(8);
    private static final Map<Class<?>, Class<?>> primitiveToWrapperTypeMap = new IdentityHashMap<>(8);

    static {
        primitiveWrapperToPrimitiveTypeMap.put(Boolean.class, boolean.class);
        primitiveWrapperToPrimitiveTypeMap.put(Byte.class, byte.class);
        primitiveWrapperToPrimitiveTypeMap.put(Character.class, char.class);
        primitiveWrapperToPrimitiveTypeMap.put(Double.class, double.class);
        primitiveWrapperToPrimitiveTypeMap.put(Float.class, float.class);
        primitiveWrapperToPrimitiveTypeMap.put(Integer.class, int.class);
        primitiveWrapperToPrimitiveTypeMap.put(Long.class, long.class);
        primitiveWrapperToPrimitiveTypeMap.put(Short.class, short.class);

        primitiveWrapperToPrimitiveTypeMap.forEach((key, value) -> {
            primitiveToWrapperTypeMap.put(value, key);
        });
    }
    
    private ClassUtils() {
        
        
    }
    
    
    public static Class<?> resolvePrimitiveAsWrapperIfNessecary(Class<?> clazz) {
        AssertUtils.notNull(clazz, "Class is null");
        
        if(!clazz.isPrimitive() || clazz == void.class) {
            return clazz;
        }
        
        return primitiveToWrapperTypeMap.get(clazz);
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
        }
        else {
            Class<?> resolvedWrapper = primitiveToWrapperTypeMap.get(rightClass);
            if (resolvedWrapper != null && leftClass.isAssignableFrom(resolvedWrapper)) {
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean isCollection(Class<?> clazz) {
        if(clazz==null) {
            return false;
        }
        
        return Collection.class.isAssignableFrom(clazz);
    }
    
    public static boolean isArray(Class<?> clazz) {
        if(clazz==null) {
            return false;
        }
        return clazz.isArray();
    }
    
    
    public static boolean isMap(Class<?> clazz) {
        if(clazz==null) {
            return false;
        }
        
        return Map.class.isAssignableFrom(clazz);
    }
}
