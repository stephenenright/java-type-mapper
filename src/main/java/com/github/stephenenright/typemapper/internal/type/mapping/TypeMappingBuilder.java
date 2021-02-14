package com.github.stephenenright.typemapper.internal.type.mapping;

import com.github.stephenenright.typemapper.internal.TypeMappingContextImpl;

public interface TypeMappingBuilder {

    public <S, D> void buildMappings(S source, TypeMappingInfo<S, D> typeMappingInfo,
            TypeMappingContextImpl<S, D> contextImpl, TypeMappingInfoRegistry typeMappingInfoRegistry);
}
