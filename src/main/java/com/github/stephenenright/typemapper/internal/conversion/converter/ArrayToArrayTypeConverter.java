package com.github.stephenenright.typemapper.internal.conversion.converter;

import java.lang.reflect.Array;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConditionalConverter;
import com.github.stephenenright.typemapper.internal.util.ArrayUtils;

public class ArrayToArrayTypeConverter implements TypeConditionalConverter<Object, Object> {

    public static ArrayToArrayTypeConverter INSTANCE = new ArrayToArrayTypeConverter();

    @Override
    public Object convert(TypeMappingContext<Object, Object> context) {
        Object source = context.getSource();
        if (source == null) {
            return null;
        }

        Object destinationObject = ArrayUtils.newInstance(context);
        Class<?> arrayElementType = ArrayUtils.getElementDestinationType(context);

        final int sourceArrayLength = ArrayUtils.getLength(source);

        for (int i = 0; i < sourceArrayLength; i++) {
            Object arrSourceElement = Array.get(source, i);
            Object arrDestElement = context.hasDestination() ? ArrayUtils.getArrayElementSafe(destinationObject, i)
                    : null;

            if (arrSourceElement != null) {
                TypeMappingContext<?, ?> elementContext = null;
                
                if(arrDestElement==null) {
                    elementContext = context.createChild(arrSourceElement, arrayElementType);
                }
                else {
                    elementContext = context.createChildForObject(arrSourceElement, arrDestElement);
                }
                
                arrDestElement = elementContext.getMappingService().map(elementContext);
            }

            Array.set(destinationObject, i, arrDestElement);
        }

        return destinationObject;
    }

    @Override
    public MatchResult matches(Class<?> sourceType, Class<?> destinationType) {
        if (sourceType.isArray() && destinationType.isArray()) {
            return MatchResult.FULL;
        }

        return MatchResult.NONE;
    }
}
