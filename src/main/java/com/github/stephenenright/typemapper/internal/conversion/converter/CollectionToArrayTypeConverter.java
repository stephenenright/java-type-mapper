package com.github.stephenenright.typemapper.internal.conversion.converter;

import java.lang.reflect.Array;
import java.util.Collection;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConditionalConverter;
import com.github.stephenenright.typemapper.internal.util.ArrayUtils;

@SuppressWarnings("rawtypes")
public class CollectionToArrayTypeConverter implements TypeConditionalConverter<Collection, Object> {

    public static final CollectionToArrayTypeConverter INSTANCE = new CollectionToArrayTypeConverter();

    @Override
    public Object convert(TypeMappingContext<Collection, Object> context) {
        Collection<?> sourceObject = context.getSource();

        if (sourceObject == null) {
            return null;
        }

        Object destinationObject = ArrayUtils.newInstance(context);
        Class<?> arrayElementType = ArrayUtils.getElementDestinationType(context);

        int i = 0;
        for (Object colSourceElement : sourceObject) {
            Object arrDestElement = context.hasDestination() ? ArrayUtils.getArrayElementSafe(destinationObject, i)
                    : null;

            if (colSourceElement != null) {
                TypeMappingContext<?, ?> elementContext = null;
                
                if(arrDestElement == null) {
                    elementContext = context.createChild(colSourceElement,arrayElementType);
                }
                else {
                    elementContext = context.createChildForObject(colSourceElement,arrDestElement);
                }
                
                arrDestElement = elementContext.getMappingService().map(elementContext);
            }

            Array.set(destinationObject, i, arrDestElement);
        }

        return destinationObject;

    }

    @Override
    public MatchResult matches(Class<?> sourceType, Class<?> destinationType) {
        if (Collection.class.isAssignableFrom(sourceType) && destinationType.isArray()) {
            return MatchResult.FULL;
        }

        return MatchResult.NONE;
    }

}
