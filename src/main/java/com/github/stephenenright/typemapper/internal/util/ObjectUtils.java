
package com.github.stephenenright.typemapper.internal.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.function.Supplier;

public abstract class ObjectUtils {

    private static final int HASH_CODE_BASE = 7;
    private static final int HASH_CODE_MULTIPLIER = 31;
    private static final int HASH_CODE_FOR_NULL = 0;

    private ObjectUtils() {

    }

    public static boolean areEqual(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        } else if (o1 == null || o2 == null) {
            return false;
        } else if (o1.equals(o2)) {
            return true;
        } else if (o1.getClass().isArray() && o2.getClass().isArray()) {
            return arrayEquals(o1, o2);
        }
        return false;
    }

    private static boolean arrayEquals(Object o1, Object o2) {
        if (o1 instanceof Object[] && o2 instanceof Object[]) {
            return Arrays.equals((Object[]) o1, (Object[]) o2);
        }
        if (o1 instanceof byte[] && o2 instanceof byte[]) {
            return Arrays.equals((byte[]) o1, (byte[]) o2);
        }
        if (o1 instanceof char[] && o2 instanceof char[]) {
            return Arrays.equals((char[]) o1, (char[]) o2);
        }
        if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
            return Arrays.equals((boolean[]) o1, (boolean[]) o2);
        }
        if (o1 instanceof double[] && o2 instanceof double[]) {
            return Arrays.equals((double[]) o1, (double[]) o2);
        }
        if (o1 instanceof float[] && o2 instanceof float[]) {
            return Arrays.equals((float[]) o1, (float[]) o2);
        }
        if (o1 instanceof long[] && o2 instanceof long[]) {
            return Arrays.equals((long[]) o1, (long[]) o2);
        }
        if (o1 instanceof int[] && o2 instanceof int[]) {
            return Arrays.equals((int[]) o1, (int[]) o2);
        }
        if (o1 instanceof short[] && o2 instanceof short[]) {
            return Arrays.equals((short[]) o1, (short[]) o2);
        }
        return false;
    }

    public static int hashCode(Object obj) {
        if (obj == null) {
            return HASH_CODE_FOR_NULL;
        }
        if (obj.getClass().isArray()) {
            if (obj instanceof Object[]) {
                return hashCode((Object[]) obj);
            } else if (obj instanceof byte[]) {
                return hashCode((byte[]) obj);
            } else if (obj instanceof char[]) {
                return hashCode((char[]) obj);
            } else if (obj instanceof double[]) {
                return hashCode((double[]) obj);
            } else if (obj instanceof float[]) {
                return hashCode((float[]) obj);
            } else if (obj instanceof long[]) {
                return hashCode((long[]) obj);
            } else if (obj instanceof int[]) {
                return hashCode((int[]) obj);
            } else if (obj instanceof short[]) {
                return hashCode((short[]) obj);
            } else if (obj instanceof boolean[]) {
                return hashCode((boolean[]) obj);
            }
        }
        return obj.hashCode();
    }

    public static int hashCode(Object[] array) {
        if (array == null) {
            return HASH_CODE_FOR_NULL;
        }
        int hash = HASH_CODE_BASE;
        for (Object element : array) {
            hash = HASH_CODE_MULTIPLIER * hash + hashCode(element);
        }
        return hash;
    }

    public static int hashCode(boolean[] array) {
        if (array == null) {
            return 0;
        }
        int hash = HASH_CODE_BASE;
        for (boolean element : array) {
            hash = HASH_CODE_MULTIPLIER * hash + Boolean.hashCode(element);
        }
        return hash;
    }

    public static int hashCode(byte[] array) {
        if (array == null) {
            return 0;
        }
        int hash = HASH_CODE_BASE;
        for (byte element : array) {
            hash = HASH_CODE_MULTIPLIER * hash + element;
        }
        return hash;
    }

    public static int hashCode(char[] array) {
        if (array == null) {
            return 0;
        }
        int hash = HASH_CODE_BASE;
        for (char element : array) {
            hash = HASH_CODE_MULTIPLIER * hash + element;
        }
        return hash;
    }

    public static int hashCode(double[] array) {
        if (array == null) {
            return 0;
        }
        int hash = HASH_CODE_BASE;
        for (double element : array) {
            hash = HASH_CODE_MULTIPLIER * hash + Double.hashCode(element);
        }
        return hash;
    }

    public static int hashCode(float[] array) {
        if (array == null) {
            return 0;
        }
        int hash = HASH_CODE_BASE;
        for (float element : array) {
            hash = HASH_CODE_MULTIPLIER * hash + Float.hashCode(element);
        }
        return hash;
    }

    public static int hashCode(int[] array) {
        if (array == null) {
            return 0;
        }
        int hash = HASH_CODE_BASE;
        for (int element : array) {
            hash = HASH_CODE_MULTIPLIER * hash + element;
        }
        return hash;
    }

    public static int hashCode(long[] array) {
        if (array == null) {
            return 0;
        }
        int hash = HASH_CODE_BASE;
        for (long element : array) {
            hash = HASH_CODE_MULTIPLIER * hash + Long.hashCode(element);
        }
        return hash;
    }

    public static int hashCode(short[] array) {
        if (array == null) {
            return 0;
        }
        int hash = HASH_CODE_BASE;
        for (short element : array) {
            hash = HASH_CODE_MULTIPLIER * hash + element;
        }
        return hash;
    }

    public static int hashCode(Byte b) {
        if (b == null) {
            return HASH_CODE_FOR_NULL;
        }

        return Byte.hashCode(b);
    }

    public static int hashCode(Short s) {
        if (s == null) {
            return HASH_CODE_FOR_NULL;
        }

        return Short.hashCode(s);
    }

    public static int hashCode(Integer i) {
        if (i == null) {
            return HASH_CODE_FOR_NULL;
        }

        return Integer.hashCode(i);
    }

    public static int hashCode(Long l) {
        if (l == null) {
            return HASH_CODE_FOR_NULL;
        }

        return Long.hashCode(l);
    }

    public static int hashCode(Float f) {
        if (f == null) {
            return HASH_CODE_FOR_NULL;
        }

        return Double.hashCode(f);
    }

    public static int hashCode(Double d) {
        if (d == null) {
            return HASH_CODE_FOR_NULL;
        }

        return Double.hashCode(d);
    }

    public static <T> T newInstance(Class<T> cls) throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Constructor<T> constructor = cls.getDeclaredConstructor();
        ReflectionUtils.makeAccessible(constructor);
        return constructor.newInstance();

    }

    public static <T> T newInstance(Constructor<T> constructor, Object... constructorArgs)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ReflectionUtils.makeAccessible(constructor);
        return constructor.newInstance(constructorArgs);
    }

    @SuppressWarnings("unchecked")
    public static <T> T firstNotNull(Supplier<T>... suppliers) {
        for (Supplier<T> supplier : suppliers) {
            T obj = supplier.get();
            if (obj != null)
                return obj;
        }
        return null;
    }

}
