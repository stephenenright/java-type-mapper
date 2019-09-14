package com.stephenenright.typemapper.internal.conversion.converter.factory;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConverterFactory;
import com.stephenenright.typemapper.internal.conversion.converter.TypeValueConverter;
import com.stephenenright.typemapper.internal.util.AssertUtils;
import com.stephenenright.typemapper.internal.util.ClassUtils;

public class EnumToNumberTypeConverterFactory implements TypeConverterFactory<Enum<?>, Number> {

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Number> TypeConverter<Enum<?>, T> getTypeConverter(TypeMappingContext<?, ?> context) {
        Class<?> destinationType = ClassUtils.resolvePrimitiveAsWrapperIfNessecary(context.getDestinationType());
        AssertUtils.notNull(destinationType, "Target type must not be null");

        if (Byte.class == destinationType) {
            return (TypeConverter<Enum<?>, T>) EnumToByteTypeConverter.INSTANCE;
        } else if (Short.class == destinationType) {
            return (TypeConverter<Enum<?>, T>) EnumToShortTypeConverter.INSTANCE;
        } else if (Integer.class == destinationType) {
            return (TypeConverter<Enum<?>, T>) EnumToIntegerTypeConverter.INSTANCE;
        } else if (Long.class == destinationType) {
            return (TypeConverter<Enum<?>, T>) EnumToLongTypeConverter.INSTANCE;
        } else if (Float.class == destinationType) {
            return (TypeConverter<Enum<?>, T>) EnumToFloatTypeConverter.INSTANCE;
        } else if (Double.class == destinationType) {
            return (TypeConverter<Enum<?>, T>) EnumToDoubleTypeConverter.INSTANCE;
        } else if (BigInteger.class == destinationType) {
            return (TypeConverter<Enum<?>, T>) EnumToBigIntegerTypeConverter.INSTANCE;
        } else if (BigDecimal.class == destinationType) {
            return (TypeConverter<Enum<?>, T>) EnumToBigDecimalTypeConverter.INSTANCE;
        } else {
            throw new IllegalArgumentException("Unsupported number type: " + destinationType.getName());
        }
    }

    private static abstract class EnumToNumberBaseTypeConverter<T extends Number> implements TypeConverter<Enum<?>, T> {

        protected T convertEnum(TypeMappingContext<Enum<?>, ? extends Number> context) {
            Enum<?> value = context.getSource();
            if (value == null) {
                return null;
            }

            TypeValueConverter<Number, T> converter = NumberToNumberTypeConverterFactory.INSTANCE
                    .getTypeValueConverter(context);
            return converter.convertValue(value.ordinal());
        }
    }

    private static final class EnumToByteTypeConverter extends EnumToNumberBaseTypeConverter<Byte> {

        public static final EnumToByteTypeConverter INSTANCE = new EnumToByteTypeConverter();

        @Override
        public Byte convert(TypeMappingContext<Enum<?>, Byte> context) {
            return convertEnum(context);
        }
    }

    private static final class EnumToShortTypeConverter extends EnumToNumberBaseTypeConverter<Short> {

        public static final EnumToShortTypeConverter INSTANCE = new EnumToShortTypeConverter();

        @Override
        public Short convert(TypeMappingContext<Enum<?>, Short> context) {
            return convert(context);
        }
    }

    private static final class EnumToIntegerTypeConverter extends EnumToNumberBaseTypeConverter<Integer> {

        public static final EnumToIntegerTypeConverter INSTANCE = new EnumToIntegerTypeConverter();

        @Override
        public Integer convert(TypeMappingContext<Enum<?>, Integer> context) {
            return convertEnum(context);
        }
    }

    private static final class EnumToLongTypeConverter extends EnumToNumberBaseTypeConverter<Long> {

        public static final EnumToLongTypeConverter INSTANCE = new EnumToLongTypeConverter();

        @Override
        public Long convert(TypeMappingContext<Enum<?>, Long> context) {
            return convertEnum(context);
        }
    }

    private static final class EnumToFloatTypeConverter extends EnumToNumberBaseTypeConverter<Float> {

        public static final EnumToFloatTypeConverter INSTANCE = new EnumToFloatTypeConverter();

        @Override
        public Float convert(TypeMappingContext<Enum<?>, Float> context) {
            return convertEnum(context);
        }
    }

    private static final class EnumToDoubleTypeConverter extends EnumToNumberBaseTypeConverter<Double> {

        public static final EnumToDoubleTypeConverter INSTANCE = new EnumToDoubleTypeConverter();

        @Override
        public Double convert(TypeMappingContext<Enum<?>, Double> context) {
            return convertEnum(context);
        }
    }

    private static final class EnumToBigIntegerTypeConverter extends EnumToNumberBaseTypeConverter<BigInteger> {

        public static final EnumToBigIntegerTypeConverter INSTANCE = new EnumToBigIntegerTypeConverter();

        @Override
        public BigInteger convert(TypeMappingContext<Enum<?>, BigInteger> context) {
            return convertEnum(context);
        }
    }

    private static final class EnumToBigDecimalTypeConverter extends EnumToNumberBaseTypeConverter<BigDecimal> {

        public static final EnumToBigDecimalTypeConverter INSTANCE = new EnumToBigDecimalTypeConverter();

        @Override
        public BigDecimal convert(TypeMappingContext<Enum<?>, BigDecimal> context) {
            return convertEnum(context);
        }
    }

}
