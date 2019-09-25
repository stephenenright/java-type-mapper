package com.stephenenright.typemapper.internal.type.mapping;

import com.stephenenright.typemapper.configuration.TypeMapperConfiguration;

public interface TypeMappingBuilder {

    public <S, D> void buildMappings(S source, TypeMappingInfo<S, D> typeMappingInfo,
            TypeMapperConfiguration configuration);
}
