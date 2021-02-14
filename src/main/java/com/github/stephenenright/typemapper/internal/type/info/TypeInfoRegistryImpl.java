package com.github.stephenenright.typemapper.internal.type.info;

import java.util.Map;

import com.github.stephenenright.typemapper.TypeInfo;
import com.github.stephenenright.typemapper.TypeInfoRegistry;
import com.github.stephenenright.typemapper.TypeMapperConfiguration;
import com.github.stephenenright.typemapper.internal.collection.ConcurrentReferenceHashMap;

public class TypeInfoRegistryImpl implements TypeInfoRegistry {

    private final Map<TypeInfoKey, TypeInfo<?>> typeInfoCache = new ConcurrentReferenceHashMap<>(256);

    private TypeInfoCreator typeInfoCreator;

    public TypeInfoRegistryImpl(TypeInfoCreator typeInfoCreator) {
        this.typeInfoCreator = typeInfoCreator;
    }

    @Override
    public <T> TypeInfo<T> get(Class<T> type, TypeMapperConfiguration configuration) {
        return get(null, type, configuration);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeInfo<T> get(T source, Class<T> type, TypeMapperConfiguration configuration) {
        TypeInfoKey key = new TypeInfoKey(type, configuration);
        TypeInfo<T> typeInfo = (TypeInfo<T>) typeInfoCache.get(key);

        if (typeInfo == null) {
            typeInfo = registerTypeInfo(source,key, type, configuration);
        }

        return typeInfo;
    }

    @Override
    public boolean isPossibleTypeHasProperties(Class<?> type) {
        return typeInfoCreator.isPossibleTypeHasProperties(type);
    }

    private <T> TypeInfo<T> registerTypeInfo(T source, TypeInfoKey key, Class<T> type, TypeMapperConfiguration configuration) {
        TypeInfo<T> typeInfo = typeInfoCreator.create(source, type, configuration,this);

        if (typeInfo.isCacheable()) {
            typeInfoCache.putIfAbsent(key, typeInfo);
        }

        return typeInfo;
    }
}
