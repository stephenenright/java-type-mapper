package com.stephenenright.typemapper.internal;

public class TypeMappingServiceImpl implements TypeMappingService {

    @Override
    public <S, D> D map(S src, D dest) {
        return dest;
    } 
}
