package com.github.stephenenright.typemapper.internal.util;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.github.stephenenright.typemapper.TypeMappingContext;

public class MapUtils {

    private MapUtils() {

    }
    
    public static boolean isMap(Class<?> cls) {
        return Map.class.isAssignableFrom(cls);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<Object, Object> createForContext(TypeMappingContext<Map, Map> context) {
        if (!context.getDestinationType().isInterface()) {
            return context.getMappingService().createDestination(context);
        }
        
        return createForInterface(context.getDestinationType());
    }
    
    private static Map<Object, Object> createForInterface(Class<?> mapType) {
        if (SortedMap.class.isAssignableFrom(mapType)) {
            return new TreeMap<Object, Object>();
        }

        return new HashMap<Object, Object>();
    }
}
