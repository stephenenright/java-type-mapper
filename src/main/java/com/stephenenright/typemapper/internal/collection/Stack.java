package com.stephenenright.typemapper.internal.collection;

import java.util.LinkedList;

public class Stack<T> extends LinkedList<T> {

    private static final long serialVersionUID = -1624894899104902593L;

    public void push(T element) {
        add(element);
    }

    public T pop() {
        return remove(size() - 1);
    }

    public T peek() {
        return get(size() - 1);
    }

}
