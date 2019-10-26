package com.stephenenright.typemapper.internal;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.stephenenright.typemapper.TypeInfo;
import com.stephenenright.typemapper.TypeInfoRegistry;
import com.stephenenright.typemapper.internal.collection.Stack;
import com.stephenenright.typemapper.internal.common.CommonConstants;
import com.stephenenright.typemapper.internal.common.Pair;
import com.stephenenright.typemapper.internal.common.error.Errors;
import com.stephenenright.typemapper.internal.type.info.TypePropertyGetter;
import com.stephenenright.typemapper.internal.util.ArrayUtils;
import com.stephenenright.typemapper.internal.util.ClassUtils;
import com.stephenenright.typemapper.internal.util.CollectionUtils;
import com.stephenenright.typemapper.internal.util.IterableUtils;
import com.stephenenright.typemapper.internal.util.JavaBeanUtils;
import com.stephenenright.typemapper.internal.util.ListUtils;
import com.stephenenright.typemapper.internal.util.MapUtils;
import com.stephenenright.typemapper.internal.util.PropertyPathUtils;

abstract class TypeMappingConversionStrategyMapBase implements TypeMappingConversionStrategy {

    protected final TypeInfoRegistry typeInfoRegistry;

    public TypeMappingConversionStrategyMapBase(TypeInfoRegistry typeInfoRegistry) {
        this.typeInfoRegistry = typeInfoRegistry;
    }

    protected void mapFromSourceRoot(Object sourceValue, ConversionContext<?> context) {
        Class<?> sourceType = sourceValue.getClass();

        if (ClassUtils.isPrimitive(sourceType)) {
            context.setDestinationPropertyValue(sourceValue, CommonConstants.PROPERTY_NAME_VALUE, sourceValue,
                    Boolean.FALSE, Boolean.FALSE);
        } else if (MapUtils.isMap(sourceType)) {
            mapMap(sourceValue, context);
        } else if (IterableUtils.isIterable(sourceType)) {
            context.setDestinationPropertyValue(sourceValue, CommonConstants.PROPERTY_NAME_VALUES,
                    new ArrayList<Object>(), Boolean.TRUE, Boolean.FALSE);
            if (ArrayUtils.isArray(sourceType)) {
                mapArray(sourceValue, context);
            } else { // we have a collection
                mapCollection(sourceValue, context);
            }
        } else if (JavaBeanUtils.isPossibleJavaBean(sourceType)) {
            TypeInfo<?> typeInfo = typeInfoRegistry.get(sourceType, context.getMappingContext().getConfiguration());
            mapBean(typeInfo, sourceType, sourceValue, context);
        } else { // we have an object we don't recognise so just add it as a value
            context.setDestinationPropertyValue(sourceValue, CommonConstants.PROPERTY_NAME_VALUE, sourceValue,
                    Boolean.FALSE, Boolean.FALSE);
        }
    }

    protected void mapProperty(String propertyName, Object sourceValue, ConversionContext<?> context) {
        if (sourceValue == null) {
            return;
        }

        if (!processPathForProperty(propertyName, sourceValue,context)) {
            return;
        }

        Class<?> sourceType = sourceValue.getClass();

        if (!CollectionUtils.isCollection(sourceType) && !ArrayUtils.isArray(sourceType)) {
            Object destination = context.getDestinationForSource(sourceValue);

            if (destination != null) {
                context.setDestinationPropertyValue(sourceValue, propertyName, destination, false, Boolean.FALSE);
                return;
            }
        }

        if (MapUtils.isMap(sourceType)) {
            Map<String, Object> objectMap = new HashMap<String, Object>();
            context.setDestinationPropertyValue(sourceValue, propertyName, objectMap, true, Boolean.FALSE);
            context.pathPush(propertyName);
            mapMap(sourceValue, context);
            context.pathPop();
        } else if (IterableUtils.isIterable(sourceType)) {
            List<Object> objectList = new ArrayList<>();
            context.setDestinationPropertyValue(sourceValue, propertyName, objectList, true, Boolean.FALSE);
            context.pathPush(propertyName);

            if (ArrayUtils.isArray(sourceType)) {
                mapArray(sourceValue, context);
            } else {
                mapCollection(sourceValue, context);
            }

            context.pathPop();
        } else if (JavaBeanUtils.isPossibleJavaBean(sourceType, typeInfoRegistry,
                context.mappingContext.getConfiguration())) {
            TypeInfo<?> typeInfo = typeInfoRegistry.get(sourceType, context.getMappingContext().getConfiguration());
            Map<String, TypePropertyGetter> getters = typeInfo.getPropertyGetters();

            if (!getters.isEmpty()) {
                Map<String, Object> objectMap = new HashMap<String, Object>();
                context.setDestinationPropertyValue(sourceValue, propertyName, objectMap, true, Boolean.TRUE);
                context.pathPush(propertyName);
                mapBean(typeInfo, sourceType, sourceValue, context);
                context.pathPop();
            }

        } else {
            context.setDestinationPropertyValue(sourceValue, propertyName, sourceValue, false, Boolean.FALSE);
        }
    }

    protected void mapObject(Object sourceValue, ConversionContext<?> context) {
        if (sourceValue == null) {
            return;
        }

        if (!processPath(context,sourceValue)) {
            return;
        }

        Class<?> sourceType = sourceValue.getClass();

        if (MapUtils.isMap(sourceType)) {
            Map<String, Object> objectMap = new HashMap<>();
            context.setDestinationValue(sourceValue, objectMap, true, Boolean.FALSE);
            mapMap(sourceValue, context);
        } else if (IterableUtils.isIterable(sourceType)) {
            List<Object> objectList = new ArrayList<>();
            context.setDestinationValue(sourceValue, objectList, true, Boolean.FALSE);

            if (ArrayUtils.isArray(sourceType)) {
                mapArray(sourceValue, context);
            } else {
                mapCollection(sourceValue, context);
            }
        } else if (JavaBeanUtils.isPossibleJavaBean(sourceType, typeInfoRegistry,
                context.mappingContext.getConfiguration())) {
            TypeInfo<?> typeInfo = typeInfoRegistry.get(sourceType, context.getMappingContext().getConfiguration());

            Map<String, TypePropertyGetter> getters = typeInfo.getPropertyGetters();

            if (!getters.isEmpty()) {
                Map<String, Object> objectMap = new HashMap<String, Object>();
                context.setDestinationValue(sourceValue, objectMap, true, Boolean.TRUE);
                mapBean(typeInfo, sourceType, sourceValue, context);
            }
        } else {
            context.setDestinationValue(sourceValue, sourceValue, false, Boolean.FALSE);
        }

    }

    protected void mapBean(TypeInfo<?> typeInfo, Class<?> sourceType, Object sourceValue,
            ConversionContext<?> context) {
        Map<String, TypePropertyGetter> getters = typeInfo.getPropertyGetters();

        for (Entry<String, TypePropertyGetter> getterEntry : getters.entrySet()) {
            mapProperty(getterEntry.getKey(), getterEntry.getValue().getValue(sourceValue), context);
        }
    }

    protected void mapArray(Object sourceValue, ConversionContext<?> context) {
        final int sourceArrayLength = ArrayUtils.getLength(sourceValue);

        for (int i = 0; i < sourceArrayLength; i++) {
            context.pushIndexedProperty(i);
            Object arrSourceElement = Array.get(sourceValue, i);
            mapObject(arrSourceElement, context);
            context.pathPop();
        }
    }

    protected void mapCollection(Object sourceValue, ConversionContext<?> context) {
        Collection<?> collection = (Collection<?>) sourceValue;

        int i = 0;
        for (Object value : collection) {
            context.pushIndexedProperty(i);
            mapObject(value, context);
            context.pathPop();
            i++;
        }
    }

    protected void mapMap(Object sourceValue, ConversionContext<?> context) {
        if (sourceValue == null) {
            return;
        }

        Map<?, ?> map = (Map<?, ?>) sourceValue;

        for (Entry<?, ?> entry : map.entrySet()) {
            mapProperty(entry.getKey().toString(), entry.getValue(), context);
        }
    }

    private boolean processPathForProperty(String propertyName, Object value, ConversionContext<?> context) {
        final String propertyPath = PropertyPathUtils.joinPaths(context.getCurrentPropertyPath(), propertyName);
        return context.getMappingContext().getConfiguration().isMappingIncluded(context.getCurrentPropertyPath(), propertyPath, value, typeInfoRegistry);
    }

    private boolean processPath(ConversionContext<?> context,  Object value) {
        return context.getMappingContext().getConfiguration().isMappingIncluded(context.getCurrentPropertyPathParent(), context.getCurrentPropertyPath(), value, typeInfoRegistry);
    }

    protected abstract static class ConversionContext<T> {
        protected T destinationRoot;

        protected Map<String, Object> pathToDestinationObjectCache = new HashMap<>();
        protected final TypeMappingContextImpl<?, ?> mappingContext;
        protected Stack<String> path = new Stack<String>();
        protected Set<Integer> indexPropertiesSet;
        protected boolean hasIndexedProperty = false;
        protected String currentPath;
        protected String currentPathParent;
        protected String currentPropertyPath;
        protected String currentPropertyParentPath;

        protected final Map<Object, Object> sourceToDestination;

        public ConversionContext(TypeMappingContextImpl<?, ?> mappingContext) {
            this.mappingContext = mappingContext;
            sourceToDestination = new IdentityHashMap<>();
        }

        public void pushIndexedProperty(int index) {
            if (indexPropertiesSet == null) {
                indexPropertiesSet = new HashSet<>();
            }

            indexPropertiesSet.add(path.size());
            hasIndexedProperty = true;
            path.push(PropertyPathUtils.createIndexedPropertyName(index));
            currentPath = null;
        }

        public void pathPush(String property) {
            path.push(property);
            resetCachedPaths();
        }

        public String pathPop() {
            if (path.size() > 0) {
                resetCachedPaths();
                String popped = path.pop();
                if (PropertyPathUtils.isPathIndexedPath(popped)) {
                    indexPropertiesSet.remove(path.size());
                }
                return popped;
            }

            return CommonConstants.EMPTY_STRING;
        }

        public String getCurrentPath() {
            if (currentPath != null) {
                return currentPath;
            } else if (path.size() == 0) {
                currentPath = CommonConstants.EMPTY_STRING;
                return currentPath;
            } else {
                Pair<String, String> pathPair = PropertyPathUtils.joinPathsWithParent(path, null);
                currentPathParent = pathPair.getValue1();
                currentPath = pathPair.getValue2();
                return currentPath;
            }
        }

        public String getCurrentPathParent() {
            getCurrentPath();
            return currentPathParent;
        }

        public String getCurrentPropertyPath() {
            if (!hasIndexedProperty) {
                return getCurrentPath();
            }

            if (currentPropertyPath != null) {
                return currentPropertyPath;
            } else if (indexPropertiesSet.size() == 0) {
                return getCurrentPath();
            } else {
                Pair<String, String> pathPair = PropertyPathUtils.joinPathsWithParent(path, indexPropertiesSet);
                currentPropertyParentPath = pathPair.getValue1(); 
                currentPropertyPath = pathPair.getValue2();
                return currentPropertyPath;
            }
        }

        public String getCurrentPropertyPathParent() {
            if (!hasIndexedProperty) {
                return getCurrentPathParent();
            }

            getCurrentPropertyPath();
            return currentPropertyParentPath;

        }

        public TypeMappingContextImpl<?, ?> getMappingContext() {
            return mappingContext;
        }

        public T getDestinationRoot() {
            return destinationRoot;
        }

        @SuppressWarnings("unchecked")
        public void setDestinationPropertyValue(Object sourceValue, String propertyName, Object value,
                Boolean setParent, Boolean trackSource) {

            // TODO add includes, excludes, custom conversion etc

            final String parentPath = getCurrentPath();

            if (parentPath.equals(CommonConstants.EMPTY_STRING)) {
                setRootObjectPropertyValue(propertyName, value);
            } else {
                Object parent = pathToDestinationObjectCache.get(parentPath);

                if (parent instanceof Map) {
                    Map<String, Object> parentMap = (Map<String, Object>) parent;
                    parentMap.put(propertyName, value);
                }
            }

            if (setParent) {
                pathToDestinationObjectCache.put(PropertyPathUtils.joinPaths(parentPath, propertyName), value);
            }

            if (trackSource && !sourceToDestination.containsKey(sourceValue)) {
                sourceToDestination.put(sourceValue, value);
            }
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        public void setDestinationValue(Object sourceValue, Object value, boolean setParent, Boolean trackSource) {
            // TODO add includes, excludes, custom conversion etc

            final String parentPath = getCurrentPath();

            Object parent = pathToDestinationObjectCache.get(parentPath);

            if (parent == null) {
                if (PropertyPathUtils.isPathIndexedPath(parentPath)) {
                    final String grandParentPath = PropertyPathUtils.joinPaths(path, path.size() - 1);

                    parent = pathToDestinationObjectCache.get(grandParentPath);

                    if (parent == null) {
                        parent = destinationRoot;
                    }
                } else {
                    parent = destinationRoot;
                }
            }

            if (!ListUtils.isList(parent)) {
                throw new Errors()
                        .errorMappingSourceToMap(
                                "Error parent destination container is not an instance of list for path: " + parentPath)
                        .toMappingException();

            }

            List parentList = (List) parent;
            parentList.add(value);

            if (setParent) {
                if (!pathToDestinationObjectCache.containsKey(parentPath)) {
                    pathToDestinationObjectCache.put(parentPath, value);
                }
            }

            if (trackSource && !sourceToDestination.containsKey(sourceValue)) {
                sourceToDestination.put(sourceValue, value);
            }
        }

        public Object getDestinationForSource(Object source) {
            return sourceToDestination.get(source);
        }

        protected void resetCachedPaths() {
            currentPath = null;
            currentPathParent = null;
            currentPropertyPath = null;
            currentPropertyParentPath = null;
        }

        protected abstract void setRootObjectPropertyValue(String propertyName, Object value);
    }
}
