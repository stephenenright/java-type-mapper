package com.stephenenright.typemapper.internal.conversion;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.converter.TypeConverterFactory;

abstract class TypeConverterAdapters {

    private TypeConverterAdapters() {

    }

    public static class TypeConverterFactoryAdapter implements TypeConverter<Object, Object> {

        private final TypeConverterFactory<Object, Object> delegateFactory;

        public TypeConverterFactoryAdapter(TypeConverterFactory<Object, Object> delegateFactory) {
            this.delegateFactory = delegateFactory;
        }

        @Override
        public Object convert(TypeMappingContext<Object, Object> context) {
            TypeConverter<Object, Object> converter = delegateFactory.getTypeConverter(context);

            if (converter == null) {
                return null;
            }

            return converter.convert(context);
        }
    }

}
