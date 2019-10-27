package com.stephenenright.typemapper;

@FunctionalInterface
public interface TypeTransformer<S, D> {

    public D tranform(S source, D destination);
}
