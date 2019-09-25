package com.stephenenright.typemapper.internal.util;

import java.util.LinkedList;
import java.util.List;

public abstract class CollectionUtils {

    private CollectionUtils() {

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
}
