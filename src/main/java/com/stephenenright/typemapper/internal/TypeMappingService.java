package com.stephenenright.typemapper.internal;

public interface TypeMappingService {
    
    public <S, D> D map(S src, D dest);

}
