package com.stephenenright.typemapper.exception;

public class TypeMappingException  extends RuntimeException {
    
    private static final long serialVersionUID = -2977205125815365589L;

    public TypeMappingException(String message) {
        super(message);
    }

    public TypeMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
