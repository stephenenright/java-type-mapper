package com.github.stephenenright.typemapper.internal.conversion.converter;

import java.lang.reflect.Array;
import java.util.Collection;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConditionalConverter;
import com.github.stephenenright.typemapper.internal.util.ArrayUtils;
import com.github.stephenenright.typemapper.internal.util.CollectionUtils;
import com.github.stephenenright.typemapper.internal.util.GenericTypeUtils;

@SuppressWarnings("rawtypes")
public class ArrayToCollectionTypeConverter implements TypeConditionalConverter<Object, Collection> {

    public static final ArrayToCollectionTypeConverter INSTANCE = new ArrayToCollectionTypeConverter();

    @SuppressWarnings("unchecked")
    @Override
    public Collection convert(TypeMappingContext<Object, Collection> context) {
        Object source = context.getSource();

        if (source == null) {
            return null;
        }

        final int sourceLength = ArrayUtils.getLength(source);
        Collection<Object> destinationCollection = context.getDestination();
        Collection<Object> newDestinationCollection = CollectionUtils.createCollection(context.getDestinationType());
        Class<?> elementType = GenericTypeUtils.resolveDestinationGenericType(context);

        for (int index = 0; index < sourceLength; index++) {
            Object sourceElement = Array.get(source, index);
            Object colDestElement = null;

            if (destinationCollection != null) {
                colDestElement = CollectionUtils.getElement(destinationCollection, index);
            }

            if (sourceElement != null) {
                TypeMappingContext<?, ?> elementContext = null;
                
                if(colDestElement == null) {
                    elementContext = context.createChild(sourceElement,elementType);
                }
                else {
                    elementContext = context.createChildForObject(sourceElement,colDestElement);
                }
                
                colDestElement = elementContext.getMappingService().map(elementContext);
            }

            newDestinationCollection.add(colDestElement);
        }

        if (destinationCollection != null && !destinationCollection.isEmpty()
                && !(destinationCollection.size() < sourceLength)) {

            int destIndex = 0;
            for (Object destElement : destinationCollection) {
                if (destIndex >= sourceLength) {
                    newDestinationCollection.add(destElement);
                }

                destIndex++;
            }
        }

        return newDestinationCollection;
    }

    @Override
    public MatchResult matches(Class<?> sourceType, Class<?> destinationType) {
        if (sourceType.isArray() && Collection.class.isAssignableFrom(destinationType)) {
            return MatchResult.FULL;
        }

        return MatchResult.NONE;
    }
}
