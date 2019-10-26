package com.stephenenright.typemapper;

import java.util.List;
import java.util.Map;

public class MapMapper {

    private MapMapperConfiguration configuration;
    private TypeMappingService typeMappingService;

    public MapMapper() {
        this.configuration = MapMapperConfiguration.create();
        this.typeMappingService = TypeMappingServiceFactory.getMappingService();
    }

    public <S, D extends Map<String, Object>> D map(S source) {
        return typeMappingService.mapToMap(source, configuration);
    }

    public <S, D extends Map<String, Object>> List<D> mapToList(List<S> source) {
        return typeMappingService.mapToListOfMap(source, configuration);
    }

}
