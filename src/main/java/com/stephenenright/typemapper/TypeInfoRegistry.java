package com.stephenenright.typemapper;

public interface TypeInfoRegistry {

    public <T> TypeInfo<T> get(Class<T> type, TypeMapperConfiguration configuration);
    
    public <T> TypeInfo<T> get(T source, Class<T> type, TypeMapperConfiguration configuration);

    public boolean isPossibleTypeHasProperties(Class<?> type);
}
