package com.stephenenright.typemapper.internal.type.mapping;

import java.util.List;

import com.stephenenright.typemapper.internal.collection.Stack;
import com.stephenenright.typemapper.internal.type.info.TypePropertyGetter;
import com.stephenenright.typemapper.internal.type.info.TypePropertySetter;

class PropertyMappingPath {

    private Stack<TypePropertyGetter> sourceProperties = new Stack<TypePropertyGetter>();
    private Stack<TypePropertySetter> destinationProperties = new Stack<TypePropertySetter>();
    private Stack<String> sourcePropertyPath = new Stack<String>();
    private Stack<String> destinationPropertyPath = new Stack<String>();

    public PropertyMappingPath() {

    }

    public void destinationPathPush(String propertyName, TypePropertySetter setter) {
        destinationPropertyPath.push(propertyName);
        destinationProperties.push(setter);
    }

    public void sourcePathPush(String propertyName, TypePropertyGetter getter) {
        sourcePropertyPath.push(propertyName);
        sourceProperties.push(getter);
    }

    public void destinationPathPop() {
        destinationProperties.pop();
        destinationPropertyPath.pop();
    }

    public void sourcePathPop() {
        sourcePropertyPath.pop();
        sourceProperties.pop();
    }

    public List<TypePropertyGetter> getSourceProperties() {
        return sourceProperties;
    }

    public List<TypePropertySetter> getDestinationProperties() {
        return destinationProperties;
    }

    public void sourceClear() {
        sourceProperties.clear();
        sourcePropertyPath.clear();

    }

}
