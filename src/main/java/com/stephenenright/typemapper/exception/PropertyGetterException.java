package com.stephenenright.typemapper.exception;

public class PropertyGetterException extends TypeMappingException {

    private static final long serialVersionUID = -8235574559905055763L;

    public PropertyGetterException(String message) {
        super(message);
    }

    public PropertyGetterException(String message, Throwable cause) {
        super(message, cause);
    }
}
