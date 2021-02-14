package com.github.stephenenright.typemapper.internal.util;

import java.lang.reflect.ParameterizedType;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.internal.type.UnResolvableType;
import com.github.stephenenright.typemapper.internal.type.info.TypePropertySetter;
import com.github.stephenenright.typemapper.internal.type.mapping.TypeMapping;

public class GenericTypeUtils {

    private GenericTypeUtils() {

    }

    public static Class<?> resolveDestinationGenericType(TypeMappingContext<?, ?> context) {
        TypeMapping mapping = context.getMapping();
        TypePropertySetter setter = ListUtils.getLastElement(mapping.getDestinationSetters());

        if (setter != null) {
            Class<?> elementType = TypeUtils.resolveRawArgument(setter.getGenericType(),
                    setter.getTypeInDeclaringClass());

            if (elementType != UnResolvableType.class) {
                return elementType;
            }
        }

        if (context.getGenericDestinationType() instanceof ParameterizedType) {
            return TypeUtils
                    .getRawType(((ParameterizedType) context.getGenericDestinationType()).getActualTypeArguments()[0]);

        }

        return Object.class;
    }

}
