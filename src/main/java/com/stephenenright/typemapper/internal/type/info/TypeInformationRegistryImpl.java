package com.stephenenright.typemapper.internal.type.info;

import java.util.Map;

import com.stephenenright.typemapper.configuration.TypeMapperConfiguration;
import com.stephenenright.typemapper.internal.collection.ConcurrentReferenceHashMap;

public class TypeInformationRegistryImpl implements TypeInformationRegistry {

    private final Map<TypeInformationKey, TypeInformation<?>> typeInfoCache = new ConcurrentReferenceHashMap<>(256);

    private TypeInformationCreator typeInfoCreator;

    public TypeInformationRegistryImpl(TypeInformationCreator typeInfoCreator) {
        this.typeInfoCreator = typeInfoCreator;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeInformation<T> get(Class<T> type, TypeMapperConfiguration configuration) {
        TypeInformationKey key = new TypeInformationKey(type, configuration);
        TypeInformation<T> typeInfo = (TypeInformation<T>) typeInfoCache.get(key);

        if (typeInfo == null) {
            typeInfo = registerTypeInfo(key, type, configuration);
        }

        return typeInfo;
    }

    private <T> TypeInformation<T> registerTypeInfo(TypeInformationKey key, Class<T> type,
            TypeMapperConfiguration configuration) {
        TypeInformation<T> typeInfo = typeInfoCreator.create(type, configuration);
        typeInfoCache.putIfAbsent(key, typeInfo);
        return typeInfo;

    }
}
