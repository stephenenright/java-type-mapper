package com.github.stephenenright.typemapper.internal.type.mapping;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.github.stephenenright.typemapper.TypeInfo;
import com.github.stephenenright.typemapper.TypeInfoRegistry;
import com.github.stephenenright.typemapper.TypeMapperConfiguration;
import com.github.stephenenright.typemapper.converter.TypeConditionalConverter;
import com.github.stephenenright.typemapper.converter.TypeConverter;
import com.github.stephenenright.typemapper.internal.collection.Stack;
import com.github.stephenenright.typemapper.internal.TypeMappingContextImpl;
import com.github.stephenenright.typemapper.internal.type.info.TypePropertyGetter;
import com.github.stephenenright.typemapper.internal.type.info.TypePropertySetter;

public class TypeMappingBuilderImpl implements TypeMappingBuilder {

    private final TypeInfoRegistry typeInfoRegistry;

    public TypeMappingBuilderImpl(TypeInfoRegistry typeInfoRegistry) {
        this.typeInfoRegistry = typeInfoRegistry;
    }

    @Override
    public <S, D> void buildMappings(S source, TypeMappingInfo<S, D> typeMappingInfo,
            TypeMappingContextImpl<S, D> contextImpl, TypeMappingInfoRegistry typeMappingInfoRegistry) {

        PropertyMappingPath mappingPath = new PropertyMappingPath();
        final TypeInfo<S> sourceTypeInfo = typeInfoRegistry.get(source, typeMappingInfo.getSourceType(),
                contextImpl.getConfiguration());

        TypeMappingBuilderContext<?, ?> context = new TypeMappingBuilderContext<S, D>(typeMappingInfo, mappingPath,
                sourceTypeInfo, contextImpl, typeMappingInfoRegistry);

        typeMappingInfo.setCacheable(sourceTypeInfo.isCacheable());
        context.sourceTypeInfoPush(sourceTypeInfo);
        mapDestination(typeInfoRegistry.get(typeMappingInfo.getDestinationType(), contextImpl.getConfiguration()),
                context);
    }

    private void mapDestination(TypeInfo<?> destinationTypeInfo, TypeMappingBuilderContext<?, ?> context) {
        for (Entry<String, TypePropertySetter> entry : destinationTypeInfo.getPropertySetters().entrySet()) {
            context.getPropertyMappingPath().destinationPathPush(entry.getKey(), entry.getValue());

            boolean matched = matchSource(context.getSourceTypeInfoCurrent(), entry.getKey(), entry.getValue(),
                    context);

            if (matched && !context.getMappings().isEmpty()) {
                context.getTypeMappingInfo().addMappings(context.getMappings());
                context.resetAfterMatch();
            }

            if (!matched) {
                context.getPropertyMappingPath().destinationPathPop();
            }
        }

        context.getPropertyMappingPath().destinationPathPop();
        context.getPropertyMappingPath().sourcePathPop();
        context.sourceTypeInfoPop();
    }

    private boolean matchSource(TypeInfo<?> sourceTypeInfo, String destinationPropertyName,
            TypePropertySetter destinationSetter, TypeMappingBuilderContext<?, ?> context) {
        TypePropertyGetter sourceGetter = sourceTypeInfo.getPropertyGetters().get(destinationPropertyName);

        boolean matched = false;
        if (sourceGetter != null) { // matched destination setter
            context.getPropertyMappingPath().sourcePathPush(destinationPropertyName, sourceGetter);

            TypeMappingInfo<?, ?> mappingInfo = context.getMappingInfoRegistry().get(sourceGetter.getType(),
                    destinationSetter.getType());

            if (mappingInfo != null) {
                if (mappingInfo.getConverter() != null) {
                    context.addMapping(new TypeMappingImpl(context.getPropertyMappingPath().getSourceProperties(),
                            context.getPropertyMappingPath().getDestinationProperties(), mappingInfo.getConverter()));
                } else {
                    context.cloneMappings(mappingInfo);
                }
                matched = true;
            } else {
                // we know a mapping exists from dest to source so check if its viable
                TypeConverter<?, ?> converter = context.getMappingContextImpl().getTypeConverter(sourceGetter.getType(),
                        destinationSetter.getType());

                if (converter != null) {
                    if (converter instanceof TypeConditionalConverter<?, ?>) {
                        TypeConditionalConverter.MatchResult matchResult = ((TypeConditionalConverter<?, ?>) converter)
                                .matches(sourceGetter.getType(), destinationSetter.getType());

                        if (matchResult != TypeConditionalConverter.MatchResult.NONE) {
                            // we have a viable mapping
                            context.addMapping(
                                    new TypeMappingImpl(context.getPropertyMappingPath().getSourceProperties(),
                                            context.getPropertyMappingPath().getDestinationProperties(), converter));
                            matched = true;
                        }
                    } else {
                        // we have a viable mapping
                        context.addMapping(new TypeMappingImpl(context.getPropertyMappingPath().getSourceProperties(),
                                context.getPropertyMappingPath().getDestinationProperties(), converter));
                        matched = true;
                    }
                }

            }

            if (!matched) {
                if (typeInfoRegistry.isPossibleTypeHasProperties(sourceGetter.getType())) {
                    context.sourceTypeInfoPush(sourceGetter.getTypeInfo(context.getConfiguration()));
                    TypeInfo<?> destinationTypeInfo = typeInfoRegistry.get(destinationSetter.getType(),
                            context.getConfiguration());
                    mapDestination(destinationTypeInfo, context);
                }
                else {
                    context.getPropertyMappingPath().sourcePathPop();
                }
            }
        }

        return matched;
    }

    private static class TypeMappingBuilderContext<S, D> {
        private final TypeMappingInfo<S, D> typeMappingInfo;
        private final PropertyMappingPath propertyMappingPath;
        private final TypeInfo<?> rootSourceTypeInfo;
        private final List<TypeMapping> mappings = new LinkedList<>();
        private Stack<TypeInfo<?>> sourceTypeInfoStack = new Stack<>();
        private final TypeMappingInfoRegistry typeMappingInfoRegistry;
        private TypeMappingContextImpl<S, D> typeMappingContextImpl;

        public TypeMappingBuilderContext(TypeMappingInfo<S, D> typeMappingInfo, PropertyMappingPath propertyMappingPath,
                TypeInfo<?> rootSourceTypeInfo, TypeMappingContextImpl<S, D> contextImpl,
                TypeMappingInfoRegistry typeMappingInfoRegistry) {
            this.typeMappingInfo = typeMappingInfo;
            this.propertyMappingPath = propertyMappingPath;
            this.rootSourceTypeInfo = rootSourceTypeInfo;
            sourceTypeInfoStack.push(rootSourceTypeInfo);
            this.typeMappingInfoRegistry = typeMappingInfoRegistry;
            this.typeMappingContextImpl = contextImpl;

        }

        public TypeMappingInfo<S, D> getTypeMappingInfo() {
            return typeMappingInfo;
        }

        public PropertyMappingPath getPropertyMappingPath() {
            return propertyMappingPath;
        }

        public TypeMapperConfiguration getConfiguration() {
            return typeMappingContextImpl.getConfiguration();
        }

        public TypeMappingContextImpl<S, D> getMappingContextImpl() {
            return typeMappingContextImpl;
        }

        public void addMapping(TypeMapping mapping) {
            mappings.add(mapping);
        }

        public void cloneMappings(TypeMappingInfo<?, ?> typeMappingInfo) {
            for (TypeMapping mapping : typeMappingInfo.getTypeMappings()) {
                mappings.add(new TypeMappingImpl(mapping, propertyMappingPath.getSourceProperties(),
                        propertyMappingPath.getDestinationProperties()));
            }
        }

        public TypeInfo<?> getSourceTypeInfoCurrent() {
            if (sourceTypeInfoStack.isEmpty()) {
                return rootSourceTypeInfo;
            }

            return sourceTypeInfoStack.peek();
        }

        public void sourceTypeInfoPop() {
            sourceTypeInfoStack.pop();
        }

        public void sourceTypeInfoPush(TypeInfo<?> typeInfo) {
            sourceTypeInfoStack.push(typeInfo);
        }

        public List<TypeMapping> getMappings() {
            return mappings;
        }

        public void resetAfterMatch() {
            mappings.clear();
            propertyMappingPath.sourcePathPop();
            propertyMappingPath.destinationPathPop();
        }

        public TypeMappingInfoRegistry getMappingInfoRegistry() {
            return typeMappingInfoRegistry;
        }
    }
}
