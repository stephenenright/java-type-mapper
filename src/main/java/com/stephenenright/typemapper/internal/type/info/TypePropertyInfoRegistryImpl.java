package com.stephenenright.typemapper.internal.type.info;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.stephenenright.typemapper.TypeInfoRegistry;
import com.stephenenright.typemapper.TypeMapperConfiguration;
import com.stephenenright.typemapper.internal.collection.ConcurrentReferenceHashMap;

public class TypePropertyInfoRegistryImpl implements TypePropertyInfoRegistry {

    private final Map<TypePropertyInfoKey, TypePropertyGetter> getterCache = new ConcurrentReferenceHashMap<>(256);
    private final Map<TypePropertyInfoKey, TypePropertySetter> setterCache = new ConcurrentReferenceHashMap<>(256);

    private TypeInfoRegistry typeInfoRegistry;

    public TypePropertyInfoRegistryImpl(TypeInfoRegistry typeInfoRegistry) {
        this.typeInfoRegistry = typeInfoRegistry;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TypePropertyGetter getterFor(Class<?> type, String accessorName, TypeMapperConfiguration configuration) {
        TypePropertyInfoKey key = new TypePropertyInfoKey(type, accessorName);

        if (!getterCache.containsKey(key)) {
            Set<Entry<String, TypePropertyGetter>> getterSet = typeInfoRegistry.get((Class<Object>) type, configuration)
                    .getPropertyGetters().entrySet();
            
            for (Entry<String, TypePropertyGetter> entry : getterSet) {
                addGetterToCacheIfNecessary(type, entry.getValue(), entry.getKey());
            }
        }

        return getterCache.get(key);
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public TypePropertySetter setterFor(Class<?> type, String setterName, TypeMapperConfiguration configuration) {
        TypePropertyInfoKey key = new TypePropertyInfoKey(type, setterName);

        if (!setterCache.containsKey(key)) {
            Set<Entry<String, TypePropertySetter>> setterSet = typeInfoRegistry.get((Class<Object>) type, configuration)
                    .getPropertySetters().entrySet();

            for (Entry<String, TypePropertySetter> entry : setterSet) {
                addSetterToCacheIfNecessary(type, entry.getValue(), entry.getKey());
            }
        }

        
        return setterCache.get(key);
    }

    private TypePropertyGetter addGetterToCacheIfNecessary(Class<?> type, TypePropertyGetter getter, String name) {
        TypePropertyInfoKey key = new TypePropertyInfoKey(type, name);
        TypePropertyGetter foundGetter = getterCache.get(key);
        if (foundGetter == null) {
            getterCache.put(key, getter);
        }

        return getter;
    }
    
    private TypePropertySetter addSetterToCacheIfNecessary(Class<?> type, TypePropertySetter setter, String name) {
        TypePropertyInfoKey key = new TypePropertyInfoKey(type, name);
        TypePropertySetter foundSetter = setterCache.get(key);
        if (foundSetter == null) {
            setterCache.put(key, setter);
        }

        return setter;
    }
    
    private static class TypePropertyInfoKey {
        private final Class<?> initialType;
        private final String propertyName;

        TypePropertyInfoKey(Class<?> initialType, String propertyName) {
            this.initialType = initialType;
            this.propertyName = propertyName;

        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof TypePropertyInfoKey)) {
                return false;
            }

            TypePropertyInfoKey other = (TypePropertyInfoKey) o;
            return initialType.equals(other.initialType) && propertyName.equals(other.propertyName);
        }

        @Override
        public int hashCode() {
            int result = 31 + initialType.hashCode();
            result = 31 * result + propertyName.hashCode();
            return result;
        }
    }
}
