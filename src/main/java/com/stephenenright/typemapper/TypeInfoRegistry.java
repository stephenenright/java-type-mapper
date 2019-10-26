package com.stephenenright.typemapper;

public interface TypeInfoRegistry {

    public <T> TypeInfo<T> get(Class<T> type, TypeMapperConfiguration configuration);

}
