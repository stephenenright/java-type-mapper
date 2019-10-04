package com.stephenenright.typemapper.internal.util;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.stephenenright.typemapper.TypeMappingContext;

public class MapUtils {

    private MapUtils() {

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
