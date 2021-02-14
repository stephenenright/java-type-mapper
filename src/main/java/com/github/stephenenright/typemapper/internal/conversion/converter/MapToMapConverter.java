package com.github.stephenenright.typemapper.internal.conversion.converter;

import java.util.Map;
import java.util.Map.Entry;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;
import com.github.stephenenright.typemapper.internal.type.UnResolvableType;
import com.github.stephenenright.typemapper.internal.type.info.TypePropertySetter;
import com.github.stephenenright.typemapper.internal.type.mapping.TypeMapping;
import com.github.stephenenright.typemapper.internal.util.ListUtils;
import com.github.stephenenright.typemapper.internal.util.MapUtils;
import com.github.stephenenright.typemapper.internal.util.TypeUtils;

@SuppressWarnings("rawtypes")
public class MapToMapConverter implements TypeConverter<Map, Map> {
    
    public static final MapToMapConverter INSTANCE = new MapToMapConverter();
    
    @SuppressWarnings("unchecked")
    @Override
    public Map convert(TypeMappingContext<Map, Map> context) {
        Map<?, ?> source = context.getSource();

        if (source == null) {
            return null;
        }

        Map<Object, Object> destination = context.getDestination() == null ? MapUtils.createForContext(context)
                : context.getDestination();

        TypeMapping mapping = context.getMapping();

        Class<?> keyElementType = Object.class;
        Class<?> valueElementType = Object.class;

        if (mapping != null) {
            TypePropertySetter setter = ListUtils.getLastElement(mapping.getDestinationSetters());
            Class<?>[] elementTypes = TypeUtils.resolveRawArguments(setter.getGenericType(),
                    setter.getMember().getDeclaringClass());
            if (elementTypes != null) {
                keyElementType = elementTypes[0] == UnResolvableType.class ? Object.class : elementTypes[0];
                valueElementType = elementTypes[1] == UnResolvableType.class ? Object.class : elementTypes[1];
            }
        }

        for (Entry<?, ?> entry : source.entrySet()) {
            Object key = null;
            if (entry.getKey() != null) {
                TypeMappingContext<?, ?> keyContext = context.createChild(entry.getKey(), keyElementType);
                key = context.getMappingService().map(keyContext);
            }

            Object value = null;
            if (entry.getValue() != null) {
                TypeMappingContext<?, ?> valueContext = context.createChild(entry.getValue(), valueElementType);
                value = context.getMappingService().map(valueContext);
            }

            destination.put(key, value);
        }

        return destination;
    }
}
