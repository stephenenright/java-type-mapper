package com.github.stephenenright.typemapper.internal.type.info;

public interface TypePropertySetter extends TypePropertyInfo {

    public void setValue(Object target, Object value);
}
