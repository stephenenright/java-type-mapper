package com.stephenenright.typemapper.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class CollectionUtils {

    private CollectionUtils() {

    }
    
    public static boolean isCollection(Class<?> type) {
        return Collection.class.isAssignableFrom(type);
    }
    
    public static <E> List<E> asLinkedList(E[] elements) {
        if (elements == null || elements.length == 0) {
            return new LinkedList<E>();
        }

        List<E> newList = new LinkedList<E>();

        for (E element : elements) {
            newList.add(element);
        }

        return newList;
    }

    @SuppressWarnings({ "unchecked" })
    public static <T> Collection<T> createCollection(Class<Collection> destinationType) {
        if (destinationType.isInterface()) {
            return createCollectionTypeForInterface(destinationType);
        } else {
            if (!Collection.class.isAssignableFrom(destinationType)) {
                throw new IllegalArgumentException("Not a collection type: " + destinationType.getName());
            }
            try {
                return (Collection<T>) ObjectUtils.newInstance(destinationType);
            } catch (Throwable ex) {
                throw new IllegalArgumentException("Unable to instantiate Collection: " + destinationType.getName(),
                        ex);
            }
        }
    }

    private static <T> Collection<T> createCollectionTypeForInterface(Class<?> collectionType) {

        if (Set.class == collectionType || Collection.class == collectionType) {
            return new LinkedHashSet<>();
        } else if (List.class == collectionType) {
            return new ArrayList<>();
        } else if (SortedSet.class == collectionType || NavigableSet.class == collectionType) {
            return new TreeSet<>();
        } else {
            throw new IllegalArgumentException(
                    "Unable to create default collection type, unsupported interface: " + collectionType.getName());
        }
    }

    public static Object getElement(Collection<Object> collection, int index) {
        if (collection.size() < index + 1) {
            return null;
        }

        if (collection instanceof List) {
            return ((List<Object>) collection).get(index);
        }

        Iterator<Object> iterator = collection.iterator();
        for (int i = 0; i < index; i++) {
            iterator.next();
        }
        return iterator.next();
    }

}
