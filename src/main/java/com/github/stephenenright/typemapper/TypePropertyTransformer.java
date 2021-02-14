package com.github.stephenenright.typemapper;

@FunctionalInterface
public interface TypePropertyTransformer {

    public Object tranform(Object source, Object destination, Object currentSource, Object currentDestination);
}
