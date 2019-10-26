package com.stephenenright.typemapper;

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

    public <S, D> void addTypeConverter(TypeConverter<S, D> typeConverter) {
        configuration.addTypeConverter(typeConverter);
    }

    public <S, D> void addTypeConverterFactory(TypeConverterFactory<S, D> factory) {
        configuration.addTypeConverterFactory(factory);
    }
}
