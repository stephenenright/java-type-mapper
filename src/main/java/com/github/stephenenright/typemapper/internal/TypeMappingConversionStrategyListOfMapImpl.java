package com.github.stephenenright.typemapper.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.stephenenright.typemapper.TypeInfoRegistry;
import com.github.stephenenright.typemapper.internal.common.error.Errors;
import com.github.stephenenright.typemapper.internal.util.ListUtils;

/**
 * Opinionated TypeMappingConversionStrategy that converts a source list of
 * object to a list of maps
 */
public class TypeMappingConversionStrategyListOfMapImpl extends TypeMappingConversionStrategyMapBase
        implements TypeMappingConversionStrategy {

    public TypeMappingConversionStrategyListOfMapImpl(TypeInfoRegistry typeInfoRegistry) {
        super(typeInfoRegistry);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S, D> D map(TypeMappingContextImpl<S, D> context) {
        S source = context.getSource();

        if (source == null) {
            return null;
        }

        if (!ListUtils.isList(source)) {
            throw new Errors()
                    .errorGeneral("Unable to map to List<Map<String,Object>> source object must be a java.util.List")
                    .toMappingException();
        }

        List<?> sourceObjectList = (List<?>) (source);

        if (sourceObjectList.isEmpty()) {
            return (D) new ArrayList<Map<String, Object>>();
        }

        ConversionContextList conversionContext = new ConversionContextList(context);

        for (Object sourceObject : sourceObjectList) {
            if (sourceObject != null) {
                conversionContext.initBuildingObject();
            }
            mapFromSourceRoot(sourceObject, conversionContext);
            conversionContext.resetAfterObjectBuilt();
        }

        return (D) conversionContext.getDestinationRoot();
    }

    private static class ConversionContextList extends ConversionContext<List<Map<String, Object>>> {

        private Map<String, Object> buildingObject;

        public ConversionContextList(TypeMappingContextImpl<?, ?> mappingContext) {
            super(mappingContext);
            destinationRoot = new ArrayList<>();
        }

        @Override
        protected void setRootObjectPropertyValue(String propertyName, Object value) {
            buildingObject.put(propertyName, value);
        }

        public void initBuildingObject() {
            buildingObject = new HashMap<String, Object>();
        }

        public void resetAfterObjectBuilt() {
            pathToDestinationObjectCache.clear();
            path.clear();

            if (indexPropertiesSet != null) {
                indexPropertiesSet = null;
            }

            hasIndexedProperty = false;

            resetCachedPaths();

            sourceToDestination.clear();
            destinationRoot.add(buildingObject);
            buildingObject = null;
        }

        @Override
        public Object getBuildingDestination() {
            return buildingObject;
        }

    }
}
