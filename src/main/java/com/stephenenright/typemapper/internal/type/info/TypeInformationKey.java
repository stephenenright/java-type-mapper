package com.stephenenright.typemapper.internal.type.info;

import com.stephenenright.typemapper.configuration.TypeMapperConfiguration;

class TypeInformationKey {

    private final Class<?> type;
    private final TypeMapperConfiguration configuration;

    public TypeInformationKey(Class<?> type, TypeMapperConfiguration configuration) {
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

        if (!(obj instanceof TypeInformation)) {
            return false;
        }

        TypeInformation<?> typeInfo = (TypeInformation<?>) obj;
        return type.equals(typeInfo.getType()) && configuration.equals(typeInfo.getConfiguration());
    }

    @Override
    public int hashCode() {
        int result = 31 * type.hashCode();
        return 31 * result + configuration.hashCode();
    }

}
