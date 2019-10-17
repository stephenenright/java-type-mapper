package com.stephenenright.typemapper.internal.type.info;

public abstract class TypeInfoUtils {

    public boolean hasProperties(TypeInfo<?> typeInfo) {
        return !typeInfo.getPropertyGetters().isEmpty() || !typeInfo.getPropertySetters().isEmpty();
    }

}
