package com.stephenenright.typemapper.internal.conversion;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConverterFactory;
import com.stephenenright.typemapper.converter.TypeConditionalConverter;
import com.stephenenright.typemapper.converter.TypeConditionalConverter.MatchResult;
import com.stephenenright.typemapper.internal.collection.ConcurrentReferenceHashMap;
import com.stephenenright.typemapper.internal.conversion.TypeConverterAdapters.TypeConverterFactoryAdapter;
import com.stephenenright.typemapper.internal.conversion.converter.NullableTypeConverter;
import com.stephenenright.typemapper.internal.util.ReflectionUtils;

public class TypeConverterCollectionImpl implements TypeConverterCollection {

    private final Map<TypeConverterKey, TypeConverterCollectionValue> convertersMap = new HashMap<>(16);
    private final Map<TypeConverterKey, TypeConverter<?, ?>> convertersCache = new ConcurrentReferenceHashMap<>(64);

    @Override
    public void add(TypeConverter<?, ?> converter) {
        registerTypeConverter(converter);
        clearCache();

    }

    @Override
    public void add(TypeConverterFactory<?, ?> factory) {
        registerTypeConverterFactory(factory);
        clearCache();
    }

    @Override
    public TypeConverter<?, ?> findConverter(Class<?> sourceType, Class<?> destinationType) {
        TypeConverterKey key = new TypeConverterKey(sourceType, destinationType);
        TypeConverter<?, ?> converter = convertersCache.get(key);

        if (converter != null) {
            return converter;
        }

        final List<Class<?>> sourceHierarchy = TypeConverterUtils.findAncestorsForType(sourceType);
        final List<Class<?>> targetHierarchy = TypeConverterUtils.findAncestorsForType(destinationType);

        for (Class<?> source : sourceHierarchy) {
            for (Class<?> target : targetHierarchy) {
                TypeConverterCollectionValue colValue = convertersMap.get(new TypeConverterKey(source, target));

                if (colValue != null) {
                    TypeConverter<?, ?> matchingConverter = findMatchingConverter(sourceType, destinationType,
                            colValue);

                    if (matchingConverter != null) {
                        convertersCache.put(key, matchingConverter);
                        return matchingConverter;
                    }
                }
            }
        }

        convertersCache.put(key, NullableTypeConverter.INSTANCE);
        return null;
    }

    private TypeConverter<?, ?> findMatchingConverter(Class<?> sourceType, Class<?> destinationType,
            TypeConverterCollectionValue colValue) {

        if (colValue == null) {
            return null;
        }

        TypeConverter<?, ?> foundConverter = null;
        // if we have predicate converters then lets try to match on them
        if (colValue.hasPredicateConverters()) {
            for (TypeConditionalConverter<?, ?> converter : colValue.getPredicateConverterList()) {
                MatchResult predicateResult = converter.test(sourceType, destinationType);
                if (predicateResult == MatchResult.FULL) {
                    return converter;
                } else if (foundConverter == null && predicateResult == MatchResult.PARTIAL) {
                    foundConverter = converter;
                }
            }
        }

        if (foundConverter != null) {
            return foundConverter;
        }

        // otherwise return the converter
        return colValue.getConverter();

    }

    @SuppressWarnings("rawtypes")
    private void registerTypeConverter(TypeConverter<?, ?> converter) {
        Type[] typeArguments = ReflectionUtils.getGenericInterfaceTypeParameters(converter.getClass());

        if (typeArguments == null || typeArguments.length < 2) {
            throw new IllegalArgumentException("Unable to register converter.  "
                    + "Unable to resolve generic type arguments for: " + converter.getClass());
        }

        TypeConverterKey key = new TypeConverterKey((Class<?>) typeArguments[0], (Class<?>) typeArguments[1]);
        TypeConverterCollectionValue colValue = convertersMap.get(key);

        if (colValue == null) {
            colValue = new TypeConverterCollectionValue(null);
        }

        if (converter instanceof TypeConditionalConverter) {
            colValue.addPredicateConverter((TypeConditionalConverter) converter);
        } else {
            colValue.setConverter(converter);
        }

        convertersMap.put(key, colValue);
    }

    @SuppressWarnings("unchecked")
    private void registerTypeConverterFactory(TypeConverterFactory<?, ?> factory) {
        Type[] typeArguments = ReflectionUtils.getGenericInterfaceTypeParameters(factory.getClass());

        if (typeArguments == null || typeArguments.length < 2) {
            throw new IllegalArgumentException("Unable to register type converter factory.  "
                    + "Unable to resolve generic type arguments for: " + factory.getClass());
        }

        TypeConverterCollectionValue colValue = new TypeConverterCollectionValue(
                new TypeConverterFactoryAdapter((TypeConverterFactory<Object, Object>) factory));
        convertersMap.put(new TypeConverterKey((Class<?>) typeArguments[0], (Class<?>) typeArguments[1]), colValue);
    }

    private void clearCache() {
        convertersCache.clear();
    }
}
