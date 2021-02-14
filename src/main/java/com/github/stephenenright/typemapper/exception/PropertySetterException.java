package com.github.stephenenright.typemapper.exception;

public class PropertySetterException extends TypeMappingException {

    private static final long serialVersionUID = -399655013869633302L;

    public PropertySetterException(String message) {
        super(message);
    }

    public PropertySetterException(String message, Throwable cause) {
        super(message, cause);
    }
}
