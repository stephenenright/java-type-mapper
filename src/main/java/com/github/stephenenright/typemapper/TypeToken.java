package com.github.stephenenright.typemapper;

import java.lang.reflect.Type;

import com.github.stephenenright.typemapper.internal.util.AssertUtils;
import com.github.stephenenright.typemapper.internal.util.ReflectionUtils;
import com.github.stephenenright.typemapper.internal.util.TypeUtils;

public class TypeToken<T> {

    private final Class<T> rawType;
    private final Type type;
    private final int hashCode;

    @SuppressWarnings("unchecked")
    protected TypeToken() {
        this.type = getSuperclassTypeParameter(getClass());
        this.rawType = (Class<T>) TypeUtils.getRawType(type);
        this.hashCode = type.hashCode();
    }

    @SuppressWarnings("unchecked")
    private TypeToken(Type type) {
        AssertUtils.notNull(type);
        this.type = type;
        this.rawType = (Class<T>) TypeUtils.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    public static <T> TypeToken<T> of(Type type) {
        return new TypeToken<T>(type);
    }

    public static Type getSuperclassTypeParameter(Class<?> subclass) {
        return ReflectionUtils.getSuperclassTypeParameter(subclass);
    }

    public final Class<T> getRawType() {
        return rawType;
    }

    public final Type getType() {
        return type;
    }

    @Override
    public final int hashCode() {
        return this.hashCode;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof TypeToken<?> && TypeUtils.equals(type, ((TypeToken<?>) o).type);
    }

    @Override
    public final String toString() {
        return TypeUtils.toString(type);
    }
}
