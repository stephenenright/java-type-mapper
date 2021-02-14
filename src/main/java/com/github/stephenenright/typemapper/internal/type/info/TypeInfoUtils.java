package com.github.stephenenright.typemapper.internal.type.info;

import com.github.stephenenright.typemapper.TypeInfo;

public abstract class TypeInfoUtils {

    public boolean hasProperties(TypeInfo<?> typeInfo) {
        return !typeInfo.getPropertyGetters().isEmpty() || !typeInfo.getPropertySetters().isEmpty();
    }

}
