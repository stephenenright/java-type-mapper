package com.stephenenright.typemapper.internal.common.error;

import java.lang.reflect.Member;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.exception.PropertyGetterException;
import com.stephenenright.typemapper.exception.PropertySetterException;
import com.stephenenright.typemapper.exception.TypeMappingException;
import com.stephenenright.typemapper.internal.util.TypeUtils;

public class Errors {

    private ErrorDetail error = null;
    
    
    public static RuntimeException createGenericMappingExceptionIfNecessary(Throwable t, TypeMappingContext<?, ?> context) {
        if(t instanceof TypeMappingException) {
            return (TypeMappingException) t;
        }
        
        return new Errors().errorGenericMapping(context.getSourceType(), context.getDestinationType(), t).toMappingException();
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
    
    public Errors errorGenericMapping(Class<?> sourceType, Class<?> destinationType,  Throwable t) {

        String message = String.format("Mapping failed from %s to %s",
                sourceType.getName(), destinationType.getName());

        return addError(new ErrorDetail(message, t));
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
