package com.stephenenright.typemapper.internal.conversion.converter;

import java.util.Collection;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.internal.util.CollectionUtils;
import com.stephenenright.typemapper.internal.util.GenericTypeUtils;

@SuppressWarnings("rawtypes")
public class CollectionToCollectionTypeConverter implements TypeConverter<Collection, Collection> {

    public static final CollectionToCollectionTypeConverter INSTANCE = new CollectionToCollectionTypeConverter();

    
    @SuppressWarnings("unchecked")
    @Override
    public Collection convert(TypeMappingContext<Collection, Collection> context) {
        Collection<?> source = context.getSource();

        if (source == null) {
            return null;
        }

        int sourceLength = source.size();
        Collection<Object> destinationCollection = context.getDestination();
        Collection newDestinationCollection = CollectionUtils.createCollection(context.getDestinationType());
        Class<?> elementType = GenericTypeUtils.resolveDestinationGenericType(context);

        int index = 0;

        for (Object sourceElement : source) {
            Object element = null;

            if (destinationCollection != null) {
                element = CollectionUtils.getElement(destinationCollection, index);
            }

            if (sourceElement != null) {
                TypeMappingContext<?, ?> elementContext = null;
                
                if(element==null) {
                    elementContext = context.createChild(sourceElement, elementType);
                }
                else {
                    elementContext = context.createChildForObject(sourceElement, element);
                }
                
                element = elementContext.getMappingService().map(elementContext);
            }

            newDestinationCollection.add(element);

            index++;
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
}
