package com.github.stephenenright.typemapper.internal.type.info;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.stephenenright.typemapper.TypeInfoRegistry;
import com.github.stephenenright.typemapper.TypeMapperConfiguration;

public class TypeInfoMapImpl<T> extends TypeInfoBaseImpl<T> {

    private final T source;
    private TypeInfoRegistry infoRegistry;

    public TypeInfoMapImpl(T source, Class<T> type, TypeMapperConfiguration configuration,
            Map<String, TypePropertyGetter> propertyGetters, Map<String, TypePropertySetter> propertySetters,
            TypeInfoRegistry infoRegistry) {
        super(type, configuration, propertyGetters, propertySetters);
        this.source = source;
        this.infoRegistry = infoRegistry;
    }

    @Override
    public Map<String, TypePropertyGetter> getPropertyGetters() {
        if (propertyGetters == null) {
            synchronized (this) {
                collectProperties();
            }
        }

        return propertyGetters;
    }

    @Override
    public Map<String, TypePropertySetter> getPropertySetters() {
        if (propertySetters == null) {
            synchronized (this) {
                collectProperties();
            }
        }

        return propertySetters;
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    @SuppressWarnings("unchecked")
    private void collectProperties() {
        if (source == null || !Map.class.isAssignableFrom(source.getClass())) {
            propertyGetters = new HashMap<>();
            propertySetters = new HashMap<>();
            return;

        }

        Map<String, Object> mapSource = (Map<String, Object>) source;
        Map<String, TypePropertyGetter> getterMap = new HashMap<>();
        Map<String, TypePropertySetter> setterMap = new HashMap<>();

        for (Entry<String, Object> entry : mapSource.entrySet()) {
            Object value = entry.getValue();
            Class<?> entryType = value != null ? value.getClass() : Object.class;

            boolean isMapInstance = value instanceof Map;

            getterMap.put(entry.getKey(), new TypePropertyMapGetterImpl(
                    isMapInstance ? (Map<String, Object>) value : null, entry.getKey(), entryType, null, infoRegistry));
            setterMap.put(entry.getKey(), new TypePropertyMapSetterImpl(
                    isMapInstance ? (Map<String, Object>) value : null, entry.getKey(), entryType, null, infoRegistry));
        }

        this.propertyGetters = getterMap;
        this.propertySetters = setterMap;

    }

}
