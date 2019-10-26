package com.stephenenright.typemapper.internal.type.info;

import com.stephenenright.typemapper.TypeInfo;

public abstract class TypeInfoUtils {

    public boolean hasProperties(TypeInfo<?> typeInfo) {
        return !typeInfo.getPropertyGetters().isEmpty() || !typeInfo.getPropertySetters().isEmpty();
    }

}
