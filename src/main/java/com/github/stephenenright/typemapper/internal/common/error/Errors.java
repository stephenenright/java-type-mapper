package com.github.stephenenright.typemapper.internal.common.error;

import java.lang.reflect.Member;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;
import com.github.stephenenright.typemapper.exception.PropertyGetterException;
import com.github.stephenenright.typemapper.exception.PropertySetterException;
import com.github.stephenenright.typemapper.exception.TypeMappingException;
import com.github.stephenenright.typemapper.internal.util.TypeUtils;

public class Errors {

    private ErrorDetail error = null;

    public static RuntimeException createGenericMappingExceptionIfNecessary(Throwable t,
            TypeMappingContext<?, ?> context) {
        if (t instanceof TypeMappingException) {
            return (TypeMappingException) t;
        }

        return new Errors().errorGenericMapping(context.getSourceType(), context.getDestinationType(), t)
                .toMappingException();
    }

    public Errors errorSettingPropertyValue(Member member, Object value, Throwable t) {
        String message = String.format("Unable to set property value: %s on member: %s", value,
                TypeUtils.toString(member));
        return addError(new ErrorDetail(message, t));
    }

    public Errors errorGettingPropertyValue(Member member, Throwable t) {
        String message = String.format("Unable to get value from member: %s", TypeUtils.toString(member));
        return addError(new ErrorDetail(message, t));
    }

    public Errors errorTypeConversion(Class<?> sourceType, Class<?> destinationType, TypeConverter<?, ?> converter,
            Throwable t) {

        String message = String.format("Conversion failed with converter %s from %s to %s",
                converter.getClass().getName(), sourceType.getName(), destinationType.getName());

        return addError(new ErrorDetail(message, t));
    }

    public Errors errorCreatingDestination(Class<?> type, Throwable t) {
        String message = String.format(
                "Unable to create instance of %s. Please ensure there is a non private no argument constructor",
                type.getName());

        return addError(new ErrorDetail(message, t));
    }

    public Errors errorMappingSourceToMap(String message) {
        return addError(new ErrorDetail(message, null));
    }

    public Errors errorGenericMapping(Class<?> sourceType, Class<?> destinationType, Throwable t) {

        String message = String.format("Mapping failed from %s to %s", sourceType.getName(), destinationType.getName());

        return addError(new ErrorDetail(message, t));
    }

    public Errors errorMappingPropertyTransformation(String property, Class<?> expectedDestinationType,
            Class<?> destinationType) {
        String message = String.format(
                "Mapping failed for transformation for property %s as type: %s is not assignable to %s ", property,
                destinationType.getName(), expectedDestinationType.getName());
        return addError(new ErrorDetail(message));
    }

    public Errors errorGeneral(String message) {
        return addError(new ErrorDetail(message, null));
    }

    public TypeMappingException toPropertyGetterMappingException() {
        return new PropertyGetterException(error.getMessage(), error.getCause());
    }

    public TypeMappingException toPropertySetterMappingException() {
        return new PropertySetterException(error.getMessage(), error.getCause());
    }

    public TypeMappingException toMappingException() {
        return new TypeMappingException(error.getMessage(), error.getCause());
    }

    private Errors addError(ErrorDetail error) {
        this.error = error;
        return this;
    }
}
