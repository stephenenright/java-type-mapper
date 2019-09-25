package com.stephenenright.typemapper.internal.type.mapping;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.stephenenright.typemapper.configuration.TypeMapperConfiguration;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConditionalConverter;
import com.stephenenright.typemapper.converter.TypeConditionalConverter.MatchResult;
import com.stephenenright.typemapper.internal.collection.Stack;
import com.stephenenright.typemapper.internal.conversion.TypeConverterRegistry;
import com.stephenenright.typemapper.internal.type.info.TypeInfo;
import com.stephenenright.typemapper.internal.type.info.TypeInfoRegistry;
import com.stephenenright.typemapper.internal.type.info.TypePropertyGetter;
import com.stephenenright.typemapper.internal.type.info.TypePropertySetter;
import com.stephenenright.typemapper.internal.util.JavaBeanUtils;

public class TypeMappingBuilderImpl implements TypeMappingBuilder {

    private final TypeInfoRegistry typeInfoRegistry;
    private final TypeMappingInfoRegistry typeMappingInfoRegistry;
    private final TypeConverterRegistry typeConverterRegistry;

    public TypeMappingBuilderImpl(TypeInfoRegistry typeInfoRegistry, TypeMappingInfoRegistry typeMappingInfoRegistry,
            TypeConverterRegistry typeConverterRegistry) {
        this.typeInfoRegistry = typeInfoRegistry;
        this.typeMappingInfoRegistry = typeMappingInfoRegistry;
        this.typeConverterRegistry = typeConverterRegistry;
    }

    @Override
    public <S, D> void buildMappings(S source, TypeMappingInfo<S, D> typeMappingInfo,
            TypeMapperConfiguration configuration) {

        PropertyMappingPath mappingPath = new PropertyMappingPath();
        final TypeInfo<S> sourceTypeInfo = typeInfoRegistry.get(typeMappingInfo.getSourceType(), configuration);

        TypeMappingBuilderContext<?, ?> context = new TypeMappingBuilderContext<S, D>(typeMappingInfo, mappingPath,
                sourceTypeInfo, configuration);
        
        context.sourceTypeInfoPush(sourceTypeInfo);
        mapDestination(typeInfoRegistry.get(typeMappingInfo.getDestinationType(), configuration), context);
    }

    private void mapDestination(TypeInfo<?> destinationTypeInfo, TypeMappingBuilderContext<?, ?> context) {
        for (Entry<String, TypePropertySetter> entry : destinationTypeInfo.getPropertySetters().entrySet()) {
            context.getPropertyMappingPath().destinationPathPush(entry.getKey(), entry.getValue());
            
            boolean matched = matchSource(context.getSourceTypeInfoCurrent(), entry.getKey(), entry.getValue(),context);

            if (matched && !context.getMappings().isEmpty()) {
                if (context.getMappings().size() > 1) {
                    throw new IllegalStateException("Expected a single mapping");
                }

                context.getTypeMappingInfo().addMapping(context.getMappings().get(0));
                context.resetAfterMatch();
            }
        }
        
        context.sourceTypeInfoPop();
    }

    private boolean matchSource(TypeInfo<?> sourceTypeInfo, String destinationPropertyName,
            TypePropertySetter destinationSetter, TypeMappingBuilderContext<?, ?> context) {

        TypePropertyGetter sourceGetter = sourceTypeInfo.getPropertyGetters().get(destinationPropertyName);

        boolean matched = false;
        if (sourceGetter != null) { // matched destination setter
            context.getPropertyMappingPath().sourcePathPush(destinationPropertyName, sourceGetter);

            TypeMappingInfo<?, ?> mappingInfo = typeMappingInfoRegistry.get(sourceGetter.getType(),
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
                TypeConverter<?, ?> converter = typeConverterRegistry.getConverter(sourceGetter.getType(),
                        destinationSetter.getType());

                if (converter != null) {
                    if (converter instanceof TypeConditionalConverter<?, ?>) {
                        MatchResult matchResult = ((TypeConditionalConverter<?, ?>) converter)
                                .test(sourceGetter.getType(), destinationSetter.getType());

                        if (matchResult != MatchResult.NONE) {
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
            
            if(!matched) {
                if(JavaBeanUtils.isPossibleJavaBean(sourceGetter.getType())) {
                    context.sourceTypeInfoPush(typeInfoRegistry.get(sourceGetter.getType(), context.getConfiguration()));
                    TypeInfo<?> destinationTypeInfo = typeInfoRegistry.get(destinationSetter.getType(), 
                            context.getConfiguration());
                    mapDestination(destinationTypeInfo, context);  
                } 
            }
        }

        return matched;

        // TODO maybe fail depending on config;

    }

    private static class TypeMappingBuilderContext<S, D> {
        private final TypeMappingInfo<S, D> typeMappingInfo;
        private final PropertyMappingPath propertyMappingPath;
        private final TypeInfo<?> rootSourceTypeInfo;
        private final TypeMapperConfiguration configuration;
        private final List<TypeMapping> mappings = new LinkedList<>();
        private Stack<TypeInfo<?>> sourceTypeInfoStack = new Stack<>();

        public TypeMappingBuilderContext(TypeMappingInfo<S, D> typeMappingInfo, PropertyMappingPath propertyMappingPath,
                TypeInfo<?> rootSourceTypeInfo, TypeMapperConfiguration configuration) {
            this.typeMappingInfo = typeMappingInfo;
            this.propertyMappingPath = propertyMappingPath;
            this.rootSourceTypeInfo = rootSourceTypeInfo;
            this.configuration = configuration;
            sourceTypeInfoStack.push(rootSourceTypeInfo);

        }

        public TypeMappingInfo<S, D> getTypeMappingInfo() {
            return typeMappingInfo;
        }

        public PropertyMappingPath getPropertyMappingPath() {
            return propertyMappingPath;
        }

        public TypeMapperConfiguration getConfiguration() {
            return configuration;
        }

        public void addMapping(TypeMapping mapping) {
            mappings.add(mapping);
        }

        public void cloneMappings(TypeMappingInfo<?, ?> typeMappingInfo) {
            for (TypeMapping mapping : typeMappingInfo.getTypeMappings()) {
                mappings.add(new TypeMappingImpl(mapping, mapping.getSourceGetters(), mapping.getDestinationSetters()));
            }
        }

        public TypeInfo<?> getSourceTypeInfoCurrent() {
            if(sourceTypeInfoStack.isEmpty()) {
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
    }
}