package com.stephenenright.typemapper.internal.conversion.converter.factory;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConverterFactory;
import com.stephenenright.typemapper.internal.util.AssertUtils;
import com.stephenenright.typemapper.internal.util.ClassUtils;

public class NumberToNumberTypeConverterFactory implements TypeConverterFactory<Number, Number> {

    public static NumberToNumberTypeConverterFactory INSTANCE = new NumberToNumberTypeConverterFactory();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Number> TypeConverter<Number, T> getTypeConverter(TypeMappingContext<?, ?> context) {

        Class<?> destinationType = ClassUtils.resolvePrimitiveAsWrapperIfNessecary(context.getDestinationType());
        AssertUtils.notNull(destinationType, "Target type must not be null");

        if (Byte.class == destinationType) {
            return (TypeConverter<Number, T>) NumberToByteTypeConverter.INSTANCE;
        } else if (Short.class == destinationType) {
            return (TypeConverter<Number, T>) NumberToShortTypeConverter.INSTANCE;
        } else if (Integer.class == destinationType) {
            return (TypeConverter<Number, T>) NumberToIntegerTypeConverter.INSTANCE;
        } else if (Long.class == destinationType) {
            return (TypeConverter<Number, T>) NumberToLongTypeConverter.INSTANCE;
        } else if (Float.class == destinationType) {
            return (TypeConverter<Number, T>) NumberToFloatTypeConverter.INSTANCE;
        } else if (Double.class == destinationType) {
            return (TypeConverter<Number, T>) NumberToDoubleTypeConverter.INSTANCE;
        } else if (BigInteger.class == destinationType) {
            return (TypeConverter<Number, T>) NumberToBigIntegerTypeConverter.INSTANCE;
        } else if (BigDecimal.class == destinationType) {
            return (TypeConverter<Number, T>) NumberToBigDecimalTypeConverter.INSTANCE;
        } else {
            throw new IllegalArgumentException("Unsupported number type: " + destinationType.getName());
        }
    }

    private static abstract class NumberToNumberBaseTypeConverter<T extends Number>
            implements TypeConverter<Number, T> {

        protected Number sanitize(final Number value) {
            AssertUtils.notNull(value, "Value to convert must not be null");
            return value;
        }

        protected long toLongWithBoundsCheck(final Number number, final Class<? extends Number> targetType) {
            try {

                if (number instanceof BigInteger) {
                    return ((BigInteger) number).longValueExact();
                } else if (number instanceof BigDecimal) {
                    return ((BigDecimal) number).toBigInteger().longValueExact();
                } else {
                    return number.longValue();
                }
            } catch (Exception e) {
                throw outOfBoundsException(number, targetType);
            }
        }

        protected IllegalArgumentException outOfBoundsException(final Number number, final Class<?> targetType) {
            return new IllegalArgumentException(
                    "Unable not convert number: " + number + " of type: " + number.getClass().getName()
                            + " to target type: " + targetType.getName() + ".  Number is outside of type bounds");
        }

        protected IllegalArgumentException toSmallConversionException(final Number number, final Class<?> targetType) {
            return new IllegalArgumentException(
                    "Unable not convert number: " + number + " of type: " + number.getClass().getName()
                            + " to target type: " + targetType.getName() + ".  Number is too small");
        }

        protected IllegalArgumentException toLargeConversionException(final Number number, final Class<?> targetType) {
            return new IllegalArgumentException(
                    "Unable not convert number: " + number + " of type: " + number.getClass().getName()
                            + " to target type: " + targetType.getName() + ".  Number is too large");
        }
    }

    private static final class NumberToByteTypeConverter extends NumberToNumberBaseTypeConverter<Byte> {

        public static final NumberToByteTypeConverter INSTANCE = new NumberToByteTypeConverter();

        @Override
        public Byte convert(TypeMappingContext<Number, Byte> context) {
            Number value = context.getSource();
            if (value == null) {
                return null;
            }

            Number sanitized = sanitize(value);

            if (Byte.class.isInstance(sanitized)) {
                return (Byte) sanitized;
            }

            final long longValue = toLongWithBoundsCheck(value, Byte.class);

            if (longValue > Byte.MAX_VALUE) {
                throw toLargeConversionException(value, Byte.class);
            }

            if (longValue < Byte.MIN_VALUE) {
                throw toSmallConversionException(value, Byte.class);
            }

            return Byte.valueOf(value.byteValue());
        }

    }

    private static final class NumberToShortTypeConverter extends NumberToNumberBaseTypeConverter<Short> {

        public static final NumberToShortTypeConverter INSTANCE = new NumberToShortTypeConverter();

        @Override
        public Short convert(TypeMappingContext<Number, Short> context) {
            Number value = context.getSource();
            if (value == null) {
                return null;
            }

            Number sanitized = sanitize(value);

            if (Short.class.isInstance(sanitized)) {
                return (Short) sanitized;
            }

            final long longValue = toLongWithBoundsCheck(value, Short.class);

            if (longValue > Short.MAX_VALUE) {
                throw toLargeConversionException(value, Short.class);
            }

            if (longValue < Short.MIN_VALUE) {
                throw toSmallConversionException(value, Short.class);
            }

            return Short.valueOf(value.byteValue());

        }
    }

    private static final class NumberToIntegerTypeConverter extends NumberToNumberBaseTypeConverter<Integer> {

        public static final NumberToIntegerTypeConverter INSTANCE = new NumberToIntegerTypeConverter();

        @Override
        public Integer convert(TypeMappingContext<Number, Integer> context) {
            Number value = context.getSource();

            if (value == null) {
                return null;
            }

            Number sanitized = sanitize(value);

            if (Integer.class.isInstance(sanitized)) {
                return (Integer) sanitized;
            }

            final long longValue = toLongWithBoundsCheck(value, Integer.class);

            if (longValue > Integer.MAX_VALUE) {
                throw toLargeConversionException(value, Integer.class);
            }

            if (longValue < Integer.MIN_VALUE) {
                throw toSmallConversionException(value, Integer.class);
            }

            return Integer.valueOf(value.byteValue());

        }

    }

    private static final class NumberToLongTypeConverter extends NumberToNumberBaseTypeConverter<Long> {

        public static final NumberToLongTypeConverter INSTANCE = new NumberToLongTypeConverter();

        @Override
        public Long convert(TypeMappingContext<Number, Long> context) {
            Number value = context.getSource();

            if (value == null) {
                return null;
            }

            Number sanitized = sanitize(value);

            if (Long.class.isInstance(sanitized)) {
                return (Long) sanitized;
            }

            toLongWithBoundsCheck(value, Long.class);
            return Long.valueOf(value.longValue());

        }
    }

    private static final class NumberToFloatTypeConverter extends NumberToNumberBaseTypeConverter<Float> {

        public static final NumberToFloatTypeConverter INSTANCE = new NumberToFloatTypeConverter();

        @Override
        public Float convert(TypeMappingContext<Number, Float> context) {
            Number value = context.getSource();

            if (value == null) {
                return null;
            }

            Number sanitized = sanitize(value);

            if (Float.class.isInstance(sanitized)) {
                return (Float) sanitized;
            }

            return Float.valueOf(value.floatValue());

        }
    }

    private static final class NumberToDoubleTypeConverter extends NumberToNumberBaseTypeConverter<Double> {

        public static final NumberToDoubleTypeConverter INSTANCE = new NumberToDoubleTypeConverter();

        @Override
        public Double convert(TypeMappingContext<Number, Double> context) {
            Number value = context.getSource();

            if (value == null) {
                return null;
            }

            Number sanitized = sanitize(value);

            if (Double.class.isInstance(sanitized)) {
                return (Double) sanitized;
            }

            return Double.valueOf(value.doubleValue());
        }
    }

    private static final class NumberToBigIntegerTypeConverter extends NumberToNumberBaseTypeConverter<BigInteger> {

        public static final NumberToBigIntegerTypeConverter INSTANCE = new NumberToBigIntegerTypeConverter();

        @Override
        public BigInteger convert(TypeMappingContext<Number, BigInteger> context) {
            Number value = context.getSource();

            if (value == null) {
                return null;
            }

            Number sanitized = sanitize(value);

            if (BigInteger.class.isInstance(sanitized)) {
                return (BigInteger) sanitized;
            }

            if (value instanceof BigDecimal) {
                return ((BigDecimal) value).toBigInteger();
            } else {
                return BigInteger.valueOf(value.longValue());
            }
        }
    }

    private static final class NumberToBigDecimalTypeConverter extends NumberToNumberBaseTypeConverter<BigDecimal> {

        public static final NumberToBigDecimalTypeConverter INSTANCE = new NumberToBigDecimalTypeConverter();

        @Override
        public BigDecimal convert(TypeMappingContext<Number, BigDecimal> context) {
            Number value = context.getSource();

            if (value == null) {
                return null;
            }

            Number sanitized = sanitize(value);

            if (BigDecimal.class.isInstance(sanitized)) {
                return (BigDecimal) sanitized;
            }

            return new BigDecimal(value.toString());

        }
    }
}
