package com.stephenenright.typemapper.internal.util;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.stephenenright.typemapper.internal.collection.ConcurrentReferenceHashMap;
import com.stephenenright.typemapper.internal.type.UnResolvableType;

public abstract class TypeUtils {

    private static final Map<Class<?>, Reference<Map<TypeVariable<?>, Type>>> typesCache = new ConcurrentReferenceHashMap<>(
            256);

    private TypeUtils() {

    }

    public static Class<?> getEnumType(Class<?> targetType) {
        Class<?> enumType = targetType;
        while (enumType != null && !enumType.isEnum()) {
            enumType = enumType.getSuperclass();
        }

        return enumType;
    }

    public static Class<?> getRawType(Type type) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            AssertUtils.isTrue(rawType instanceof Class);
            return (Class<?>) rawType;
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        } else if (type instanceof TypeVariable) {
            // alternatively we can return the general type i.e. return Object.class;
            return getRawType(((TypeVariable<?>) type).getBounds()[0]);
        } else if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);

        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Could not retireve type  but <" + type + "> is of type " + className);
        }
    }

    public static boolean equal(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public static boolean equals(Type a, Type b) {
        if (a == b) {
            return true;

        } else if (a instanceof Class) {
            return a.equals(b);

        } else if (a instanceof ParameterizedType) {
            if (!(b instanceof ParameterizedType)) {
                return false;
            }

            ParameterizedType pa = (ParameterizedType) a;
            ParameterizedType pb = (ParameterizedType) b;
            return equal(pa.getOwnerType(), pb.getOwnerType()) && pa.getRawType().equals(pb.getRawType())
                    && Arrays.equals(pa.getActualTypeArguments(), pb.getActualTypeArguments());

        } else if (a instanceof GenericArrayType) {
            if (!(b instanceof GenericArrayType)) {
                return false;
            }

            GenericArrayType ga = (GenericArrayType) a;
            GenericArrayType gb = (GenericArrayType) b;
            return equals(ga.getGenericComponentType(), gb.getGenericComponentType());

        } else if (a instanceof WildcardType) {
            if (!(b instanceof WildcardType)) {
                return false;
            }

            WildcardType wa = (WildcardType) a;
            WildcardType wb = (WildcardType) b;
            return Arrays.equals(wa.getUpperBounds(), wb.getUpperBounds())
                    && Arrays.equals(wa.getLowerBounds(), wb.getLowerBounds());

        } else if (a instanceof TypeVariable) {
            if (!(b instanceof TypeVariable)) {
                return false;
            }
            TypeVariable<?> va = (TypeVariable<?>) a;
            TypeVariable<?> vb = (TypeVariable<?>) b;
            return va.getGenericDeclaration() == vb.getGenericDeclaration() && va.getName().equals(vb.getName());

        } else {
            return false;
        }
    }

    public static String toString(Type type) {
        return type instanceof Class ? ((Class<?>) type).getName() : type.toString();
    }

    public static Type resolveBound(TypeVariable<?> typeVariable) {
        Type[] bounds = typeVariable.getBounds();
        if (bounds.length == 0)
            return UnResolvableType.class;

        Type bound = bounds[0];
        if (bound instanceof TypeVariable)
            bound = resolveBound((TypeVariable<?>) bound);

        return bound == Object.class ? UnResolvableType.class : bound;
    }

    private static void collectSuperTypeArgs(final Type[] types, final Map<TypeVariable<?>, Type> map) {
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type rawType = parameterizedType.getRawType();
                if (rawType instanceof Class) {
                    collectSuperTypeArgs(((Class<?>) rawType).getGenericInterfaces(), map);
                }
            } else if (type instanceof Class) {
                collectSuperTypeArgs(((Class<?>) type).getGenericInterfaces(), map);
            }
        }
    }

    private static void collectTypeArgs(ParameterizedType type, Map<TypeVariable<?>, Type> map) {
        if (type.getRawType() instanceof Class) {
            TypeVariable<?>[] typeVariables = ((Class<?>) type.getRawType()).getTypeParameters();
            Type[] typeArguments = type.getActualTypeArguments();

            if (type.getOwnerType() != null) {
                Type owner = type.getOwnerType();
                if (owner instanceof ParameterizedType) {
                    collectTypeArgs((ParameterizedType) owner, map);
                }
            }

            for (int i = 0; i < typeArguments.length; i++) {
                TypeVariable<?> variable = typeVariables[i];
                Type typeArgument = typeArguments[i];

                if (typeArgument instanceof Class) {
                    map.put(variable, typeArgument);
                } else if (typeArgument instanceof GenericArrayType) {
                    map.put(variable, typeArgument);
                } else if (typeArgument instanceof ParameterizedType) {
                    map.put(variable, typeArgument);
                } else if (typeArgument instanceof TypeVariable) {
                    TypeVariable<?> typeVariableArgument = (TypeVariable<?>) typeArgument;
                    Type resolvedType = map.get(typeVariableArgument);
                    if (resolvedType == null)
                        resolvedType = resolveBound(typeVariableArgument);
                    map.put(variable, resolvedType);
                }
            }
        }
    }

    public static Class<?> resolveRawClass(Type genericType, Class<?> subType) {
        if (genericType instanceof Class) {
            return (Class<?>) genericType;
        } else if (genericType instanceof ParameterizedType) {
            return resolveRawClass(((ParameterizedType) genericType).getRawType(), subType);
        } else if (genericType instanceof GenericArrayType) {
            GenericArrayType arrayType = (GenericArrayType) genericType;
            Class<?> component = resolveRawClass(arrayType.getGenericComponentType(), subType);
            return Array.newInstance(component, 0).getClass();
        } else if (genericType instanceof TypeVariable) {
            TypeVariable<?> variable = (TypeVariable<?>) genericType;
            genericType = getTypeVariableMap(subType).get(variable);
            genericType = genericType == null ? TypeUtils.resolveBound(variable)
                    : resolveRawClass(genericType, subType);
        }

        return genericType instanceof Class ? (Class<?>) genericType : UnResolvableType.class;
    }

    public static <T, S extends T> Class<?> resolveRawArgument(Class<T> type, Class<S> subType) {
        return resolveRawArgument(resolveGenericType(type, subType), subType);
    }

    public static Class<?> resolveRawArgument(Type genericType, Class<?> subType) {
        Class<?>[] arguments = resolveRawArguments(genericType, subType);
        if (arguments == null)
            return UnResolvableType.class;

        if (arguments.length != 1)
            throw new IllegalArgumentException(
                    "Expected 1 argument for generic type " + genericType + " but found " + arguments.length);

        return arguments[0];
    }

    public static <T, S extends T> Class<?>[] resolveRawArguments(Class<T> type, Class<S> subType) {
        return resolveRawArguments(resolveGenericType(type, subType), subType);
    }

    private static Map<TypeVariable<?>, Type> getTypeVariableMap(final Class<?> targetType) {
        Reference<Map<TypeVariable<?>, Type>> ref = typesCache.get(targetType);
        Map<TypeVariable<?>, Type> map = ref != null ? ref.get() : null;

        if (map == null) {
            map = new HashMap<TypeVariable<?>, Type>();

            TypeUtils.collectSuperTypeArgs(targetType.getGenericInterfaces(), map);

            Type genericType = targetType.getGenericSuperclass();
            Class<?> type = targetType.getSuperclass();
            while (type != null && !Object.class.equals(type)) {
                if (genericType instanceof ParameterizedType)
                    TypeUtils.collectTypeArgs((ParameterizedType) genericType, map);
                TypeUtils.collectSuperTypeArgs(type.getGenericInterfaces(), map);

                genericType = type.getGenericSuperclass();
                type = type.getSuperclass();
            }

            type = targetType;
            while (type.isMemberClass()) {
                genericType = type.getGenericSuperclass();
                if (genericType instanceof ParameterizedType)
                    TypeUtils.collectTypeArgs((ParameterizedType) genericType, map);

                type = type.getEnclosingClass();
            }

            typesCache.put(targetType, new WeakReference<Map<TypeVariable<?>, Type>>(map));
        }

        return map;
    }

    public static Type resolveGenericType(Class<?> type, Type subType) {
        Class<?> rawType;
        if (subType instanceof ParameterizedType)
            rawType = (Class<?>) ((ParameterizedType) subType).getRawType();
        else
            rawType = (Class<?>) subType;

        if (type.equals(rawType))
            return subType;

        Type result;
        if (type.isInterface()) {
            for (Type superInterface : rawType.getGenericInterfaces())
                if (superInterface != null && !superInterface.equals(Object.class))
                    if ((result = resolveGenericType(type, superInterface)) != null)
                        return result;
        }

        Type superClass = rawType.getGenericSuperclass();
        if (superClass != null && !superClass.equals(Object.class))
            if ((result = resolveGenericType(type, superClass)) != null)
                return result;

        return null;
    }

    public static Class<?>[] resolveRawArguments(Type genericType, Class<?> subType) {
        Class<?>[] result = null;

        if (genericType instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) genericType;
            Type[] arguments = paramType.getActualTypeArguments();
            result = new Class[arguments.length];
            for (int i = 0; i < arguments.length; i++)
                result[i] = resolveRawClass(arguments[i], subType);
        } else if (genericType instanceof TypeVariable) {
            result = new Class[1];
            result[0] = resolveRawClass(genericType, subType);
        } else if (genericType instanceof Class) {
            TypeVariable<?>[] typeParams = ((Class<?>) genericType).getTypeParameters();
            result = new Class[typeParams.length];
            for (int i = 0; i < typeParams.length; i++)
                result[i] = resolveRawClass(typeParams[i], subType);
        }

        return result;
    }

}
