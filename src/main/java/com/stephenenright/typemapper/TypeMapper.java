package com.stephenenright.typemapper;

import java.util.Map;

import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConverterFactory;

public class TypeMapper {

    private DefaultMapperConfiguration configuration;
    private TypeMappingService typeMappingService;

    public TypeMapper() {
        this.configuration = DefaultMapperConfiguration.create();
        this.typeMappingService = TypeMappingServiceFactory.getMappingService();
    }

    public <D> D map(Object source, Class<D> destinationType) {
        return typeMappingService.map(source, destinationType, configuration);
    }

    public <S, D> TypeMapper addTypeConverter(TypeConverter<S, D> typeConverter) {
        configuration.addTypeConverter(typeConverter);
        return this;
    }

    public <S, D> TypeMapper addTypeConverterFactory(TypeConverterFactory<S, D> factory) {
        configuration.addTypeConverterFactory(factory);
        return this;
    }

    public TypeMapper setAccessLevel(TypeAccessLevel accessLevel) {
        configuration.setAccessLevel(accessLevel);
        return this;
    }

    public TypeMapper addIncludeMapping(String... properties) {
        configuration.addIncludeMapping(properties);
        return this;
    }

    public TypeMapper addExcludeMapping(String... properties) {
        configuration.addExcludeMapping(properties);
        return this;
    }

    public <S, D> TypeMapper setPostTransformer(TypeTransformer<S, D> postTransformer) {
        configuration.setPostTransformer(postTransformer);
        return this;
    }

}
