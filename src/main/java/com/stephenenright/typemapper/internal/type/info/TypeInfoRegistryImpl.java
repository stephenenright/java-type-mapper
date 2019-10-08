package com.stephenenright.typemapper.internal.type.info;

import java.util.Map;

import com.stephenenright.typemapper.TypeMapperConfiguration;
import com.stephenenright.typemapper.internal.collection.ConcurrentReferenceHashMap;

public class TypeInfoRegistryImpl implements TypeInfoRegistry {

    private final Map<TypeInfoKey, TypeInfo<?>> typeInfoCache = new ConcurrentReferenceHashMap<>(256);

    private TypeInfoCreator typeInfoCreator;

    public TypeInfoRegistryImpl(TypeInfoCreator typeInfoCreator) {
        this.typeInfoCreator = typeInfoCreator;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeInfo<T> get(Class<T> type, TypeMapperConfiguration configuration) {
        TypeInfoKey key = new TypeInfoKey(type, configuration);
        TypeInfo<T> typeInfo = (TypeInfo<T>) typeInfoCache.get(key);

        if (typeInfo == null) {
            typeInfo = registerTypeInfo(key, type, configuration);
        }

        return typeInfo;
    }

    private <T> TypeInfo<T> registerTypeInfo(TypeInfoKey key, Class<T> type,
            TypeMapperConfiguration configuration) {
        TypeInfo<T> typeInfo = typeInfoCreator.create(type, configuration);
        typeInfoCache.putIfAbsent(key, typeInfo);
        return typeInfo;

    }
}
