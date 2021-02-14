package com.github.stephenenright.typemapper.internal.type.info;

import com.github.stephenenright.typemapper.TypeInfo;
import com.github.stephenenright.typemapper.TypeMapperConfiguration;

class TypeInfoKey {

    private final Class<?> type;
    private final TypeMapperConfiguration configuration;

    public TypeInfoKey(Class<?> type, TypeMapperConfiguration configuration) {
        this.type = type;
        this.configuration = configuration;
    }

    public Class<?> getType() {
        return type;
    }

    public TypeMapperConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof TypeInfo)) {
            return false;
        }

        TypeInfo<?> typeInfo = (TypeInfo<?>) obj;
        return type.equals(typeInfo.getType()) && configuration.equals(typeInfo.getConfiguration());
    }

    @Override
    public int hashCode() {
        int result = 31 * type.hashCode();
        return 31 * result + configuration.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Type info for type: %s", type.getName());
    }
}
