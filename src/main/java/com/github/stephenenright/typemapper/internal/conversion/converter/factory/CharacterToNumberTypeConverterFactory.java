package com.github.stephenenright.typemapper.internal.conversion.converter.factory;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;
import com.github.stephenenright.typemapper.converter.TypeConverterFactory;
import com.github.stephenenright.typemapper.internal.conversion.converter.TypeValueConverter;
import com.github.stephenenright.typemapper.internal.util.AssertUtils;
import com.github.stephenenright.typemapper.internal.util.ClassUtils;

public class CharacterToNumberTypeConverterFactory implements TypeConverterFactory<Character, Number> {

    public static CharacterToNumberTypeConverterFactory INSTANCE = new CharacterToNumberTypeConverterFactory();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Number> TypeConverter<Character, T> getTypeConverter(TypeMappingContext<?, ?> context) {
        Class<?> destinationType = ClassUtils.resolvePrimitiveAsWrapperIfNessecary(context.getDestinationType());
        AssertUtils.notNull(destinationType, "Target type must not be null");

        if (Byte.class == destinationType) {
            return (TypeConverter<Character, T>) CharacterToByteTypeConverter.INSTANCE;
        } else if (Short.class == destinationType) {
            return (TypeConverter<Character, T>) CharacterToShortTypeConverter.INSTANCE;
        } else if (Integer.class == destinationType) {
            return (TypeConverter<Character, T>) CharacterToIntegerTypeConverter.INSTANCE;
        } else if (Long.class == destinationType) {
            return (TypeConverter<Character, T>) CharacterToLongTypeConverter.INSTANCE;
        } else if (Float.class == destinationType) {
            return (TypeConverter<Character, T>) CharacterToFloatTypeConverter.INSTANCE;
        } else if (Double.class == destinationType) {
            return (TypeConverter<Character, T>) CharacterToDoubleTypeConverter.INSTANCE;
        } else if (BigInteger.class == destinationType) {
            return (TypeConverter<Character, T>) CharacterToBigIntegerTypeConverter.INSTANCE;
        } else if (BigDecimal.class == destinationType) {
            return (TypeConverter<Character, T>) CharacterToBigDecimalTypeConverter.INSTANCE;
        } else {
            throw new IllegalArgumentException("Unsupported number type: " + destinationType.getName());
        }
    }

    private static abstract class CharacterToNumberBaseTypeConverter<T extends Number>
            implements TypeConverter<Character, T> {

        protected T convertNumber(TypeMappingContext<Character, ? extends Number> context) {
            Character value = context.getSource();
            if (value == null) {
                return null;
            }

            TypeValueConverter<Number, T> converter = NumberToNumberTypeConverterFactory.INSTANCE
                    .getTypeValueConverter(context);
            return converter.convertValue(Short.valueOf(value.toString()));
        }
    }

    private static final class CharacterToByteTypeConverter extends CharacterToNumberBaseTypeConverter<Byte> {

        public static final CharacterToByteTypeConverter INSTANCE = new CharacterToByteTypeConverter();

        @Override
        public Byte convert(TypeMappingContext<Character, Byte> context) {
            return convertNumber(context);
        }
    }

    private static final class CharacterToShortTypeConverter extends CharacterToNumberBaseTypeConverter<Short> {

        public static final CharacterToShortTypeConverter INSTANCE = new CharacterToShortTypeConverter();

        @Override
        public Short convert(TypeMappingContext<Character, Short> context) {
            return convert(context);
        }
    }

    private static final class CharacterToIntegerTypeConverter extends CharacterToNumberBaseTypeConverter<Integer> {

        public static final CharacterToIntegerTypeConverter INSTANCE = new CharacterToIntegerTypeConverter();

        @Override
        public Integer convert(TypeMappingContext<Character, Integer> context) {
            return convertNumber(context);
        }
    }

    private static final class CharacterToLongTypeConverter extends CharacterToNumberBaseTypeConverter<Long> {

        public static final CharacterToLongTypeConverter INSTANCE = new CharacterToLongTypeConverter();

        @Override
        public Long convert(TypeMappingContext<Character, Long> context) {
            return convertNumber(context);
        }
    }

    private static final class CharacterToFloatTypeConverter extends CharacterToNumberBaseTypeConverter<Float> {

        public static final CharacterToFloatTypeConverter INSTANCE = new CharacterToFloatTypeConverter();

        @Override
        public Float convert(TypeMappingContext<Character, Float> context) {
            return convertNumber(context);
        }
    }

    private static final class CharacterToDoubleTypeConverter extends CharacterToNumberBaseTypeConverter<Double> {

        public static final CharacterToDoubleTypeConverter INSTANCE = new CharacterToDoubleTypeConverter();

        @Override
        public Double convert(TypeMappingContext<Character, Double> context) {
            return convertNumber(context);
        }
    }

    private static final class CharacterToBigIntegerTypeConverter
            extends CharacterToNumberBaseTypeConverter<BigInteger> {

        public static final CharacterToBigIntegerTypeConverter INSTANCE = new CharacterToBigIntegerTypeConverter();

        @Override
        public BigInteger convert(TypeMappingContext<Character, BigInteger> context) {
            return convertNumber(context);
        }
    }

    private static final class CharacterToBigDecimalTypeConverter
            extends CharacterToNumberBaseTypeConverter<BigDecimal> {

        public static final CharacterToBigDecimalTypeConverter INSTANCE = new CharacterToBigDecimalTypeConverter();

        @Override
        public BigDecimal convert(TypeMappingContext<Character, BigDecimal> context) {
            return convertNumber(context);
        }
    }
}
