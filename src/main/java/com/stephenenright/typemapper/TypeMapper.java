package com.stephenenright.typemapper;

import java.util.List;
import java.util.Map;

import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConverterFactory;

public class TypeMapper {

    private TypeMapperConfiguration configuration;
    private TypeMappingService typeMappingService;

    public TypeMapper() {
        this.configuration = TypeMapperConfiguration.create();
        this.typeMappingService = TypeMappingServiceFactory.getMappingService();
    }

    public <D> D map(Object source, Class<D> destinationType) {
        return typeMappingService.map(source, destinationType, configuration);
    }

    public <S, D extends Map<String, Object>> D mapToMap(S source) {
        return typeMappingService.mapToMap(source, configuration);
    }

    public <S, D extends Map<String, Object>> List<D> mapToListOfMap(List<S> source) {
        return typeMappingService.mapToListOfMap(source, configuration);
    }

    public <S, D> void addTypeConverter(TypeConverter<S, D> typeConverter) {
        configuration.addTypeConverter(typeConverter);
    }

    public <S, D> void addTypeConverterFactory(TypeConverterFactory<S, D> factory) {
        configuration.addTypeConverterFactory(factory);
    }
}
