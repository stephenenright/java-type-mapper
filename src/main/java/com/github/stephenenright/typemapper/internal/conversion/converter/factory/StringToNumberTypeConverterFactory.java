package com.github.stephenenright.typemapper.internal.conversion.converter.factory;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;
import com.github.stephenenright.typemapper.converter.TypeConverterFactory;
import com.github.stephenenright.typemapper.internal.util.AssertUtils;
import com.github.stephenenright.typemapper.internal.util.ClassUtils;
import com.github.stephenenright.typemapper.internal.util.StringUtils;

public class StringToNumberTypeConverterFactory implements TypeConverterFactory<String, Number> {

    public static StringToNumberTypeConverterFactory INSTANCE = new StringToNumberTypeConverterFactory();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Number> TypeConverter<String, T> getTypeConverter(TypeMappingContext<?, ?> context) {
        Class<?> destinationType = ClassUtils.resolvePrimitiveAsWrapperIfNessecary(context.getDestinationType());
        AssertUtils.notNull(destinationType, "Target type must not be null");

        if (Byte.class == destinationType) {
            return (TypeConverter<String, T>) StringToByteTypeConverter.INSTANCE;
        } else if (Short.class == destinationType) {
            return (TypeConverter<String, T>) StringToShortTypeConverter.INSTANCE;
        } else if (Integer.class == destinationType) {
            return (TypeConverter<String, T>) StringToIntegerTypeConverter.INSTANCE;
        } else if (Long.class == destinationType) {
            return (TypeConverter<String, T>) StringToLongTypeConverter.INSTANCE;
        } else if (Float.class == destinationType) {
            return (TypeConverter<String, T>) StringToFloatTypeConverter.INSTANCE;
        } else if (Double.class == destinationType) {
            return (TypeConverter<String, T>) StringToDoubleTypeConverter.INSTANCE;
        } else if (BigInteger.class == destinationType) {
            return (TypeConverter<String, T>) StringToBigIntegerTypeConverter.INSTANCE;
        } else if (BigDecimal.class == destinationType) {
            return (TypeConverter<String, T>) StringToBigDecimalTypeConverter.INSTANCE;
        } else {
            return null;
        }
    }

    private static abstract class StringToNumberBaseTypeConverter<T extends Number>
            implements TypeConverter<String, T> {

        protected String sanitize(final String value) {
            AssertUtils.notNull(value, "Value to convert must not be null");
            return StringUtils.trimAll(value);
        }

        protected boolean isHex(final String value) {
            int offset = 0;

            if (value.startsWith("-")) {
                offset = 1;
            }

            return (value.startsWith("0X", offset) || value.startsWith("0x", offset) || value.startsWith("#", offset));
        }
    }

    private static final class StringToByteTypeConverter extends StringToNumberBaseTypeConverter<Byte> {

        public static final StringToByteTypeConverter INSTANCE = new StringToByteTypeConverter();

        @Override
        public Byte convert(TypeMappingContext<String, Byte> context) {
            String value = context.getSource();

            if (value == null) {
                return null;
            }

            final String sanitizedValue = sanitize(value);

            if (isHex(sanitizedValue)) {
                return Byte.decode(sanitizedValue);
            }

            return Byte.valueOf(sanitizedValue);
        }
    }

    private static final class StringToShortTypeConverter extends StringToNumberBaseTypeConverter<Short> {

        public static final StringToShortTypeConverter INSTANCE = new StringToShortTypeConverter();

        @Override
        public Short convert(TypeMappingContext<String, Short> context) {
            String value = context.getSource();

            if (value == null) {
                return null;
            }

            final String sanitizedValue = sanitize(value);

            if (isHex(sanitizedValue)) {
                return Short.decode(sanitizedValue);
            }

            return Short.valueOf(sanitizedValue);

        }
    }

    private static final class StringToIntegerTypeConverter extends StringToNumberBaseTypeConverter<Integer> {

        public static final StringToIntegerTypeConverter INSTANCE = new StringToIntegerTypeConverter();

        @Override
        public Integer convert(TypeMappingContext<String, Integer> context) {
            String value = context.getSource();

            if (value == null) {
                return null;
            }

            final String sanitizedValue = sanitize(value);

            if (isHex(sanitizedValue)) {
                return Integer.decode(sanitizedValue);
            }

            return Integer.valueOf(sanitizedValue);
        }
    }

    private static final class StringToLongTypeConverter extends StringToNumberBaseTypeConverter<Long> {

        public static final StringToLongTypeConverter INSTANCE = new StringToLongTypeConverter();

        @Override
        public Long convert(TypeMappingContext<String, Long> context) {
            String value = context.getSource();

            if (value == null) {
                return null;
            }

            final String sanitizedValue = sanitize(value);

            if (isHex(sanitizedValue)) {
                return Long.decode(sanitizedValue);
            }

            return Long.valueOf(sanitizedValue);
        }
    }

    private static final class StringToFloatTypeConverter extends StringToNumberBaseTypeConverter<Float> {

        public static final StringToFloatTypeConverter INSTANCE = new StringToFloatTypeConverter();

        @Override
        public Float convert(TypeMappingContext<String, Float> context) {
            String value = context.getSource();

            if (value == null) {
                return null;
            }

            return Float.valueOf(sanitize(value));
        }
    }

    private static final class StringToDoubleTypeConverter extends StringToNumberBaseTypeConverter<Double> {

        public static final StringToDoubleTypeConverter INSTANCE = new StringToDoubleTypeConverter();

        @Override
        public Double convert(TypeMappingContext<String, Double> context) {
            String value = context.getSource();

            if (value == null) {
                return null;
            }

            return Double.valueOf(sanitize(value));
        }
    }

    private static final class StringToBigIntegerTypeConverter extends StringToNumberBaseTypeConverter<BigInteger> {

        public static final StringToBigIntegerTypeConverter INSTANCE = new StringToBigIntegerTypeConverter();

        @Override
        public BigInteger convert(TypeMappingContext<String, BigInteger> context) {
            String value = context.getSource();

            if (value == null) {
                return null;
            }

            final String sanitizedValue = sanitize(value);

            if (isHex(sanitizedValue)) {
                return decode(sanitizedValue);
            }

            return new BigInteger(sanitizedValue);
        }

        private BigInteger decode(String value) {
            int radix = 10;
            int index = 0;

            boolean negative = false;

            if (value.startsWith("-")) {
                negative = true;
                index++;
            }

            if (value.startsWith("0x", index) || value.startsWith("0X", index)) {
                index += 2;
                radix = 16;
            } else if (value.startsWith("0", index) && value.length() > 1 + index) {
                index++;
                radix = 8;
            } else if (value.startsWith("#", index)) {
                index++;
                radix = 16;
            }

            if (negative) {
                return new BigInteger(value.substring(index), radix).negate();
            }

            return new BigInteger(value.substring(index), radix);
        }
    }

    private static final class StringToBigDecimalTypeConverter extends StringToNumberBaseTypeConverter<BigDecimal> {

        public static final StringToBigDecimalTypeConverter INSTANCE = new StringToBigDecimalTypeConverter();

        @Override
        public BigDecimal convert(TypeMappingContext<String, BigDecimal> context) {
            String value = context.getSource();

            if (value == null) {
                return null;
            }

            return new BigDecimal(sanitize(value));
        }
    }
}
