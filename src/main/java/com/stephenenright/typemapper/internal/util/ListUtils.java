package com.stephenenright.typemapper.internal.util;

import java.util.List;

public abstract class ListUtils {

    private ListUtils() {

    }

    public static <E> E getLastElement(List<E> list) {
        if (list.isEmpty()) {
            return null;
        }

        return list.get(list.size() - 1);

    }

}
