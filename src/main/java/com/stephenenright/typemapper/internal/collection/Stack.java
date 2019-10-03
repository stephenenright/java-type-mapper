package com.stephenenright.typemapper.internal.collection;

import java.util.LinkedList;

public class Stack<T> extends LinkedList<T> {

    private static final long serialVersionUID = -1624894899104902593L;

    public void push(T element) {
        add(element);
    }

    public T pop() {
        if (size() > 0) {
            return remove(size() - 1);
        }
        return null;

    }

    public T peek() {
        return get(size() - 1);
    }

}
