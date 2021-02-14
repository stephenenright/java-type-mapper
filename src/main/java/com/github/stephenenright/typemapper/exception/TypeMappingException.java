package com.github.stephenenright.typemapper.exception;

import com.github.stephenenright.typemapper.internal.util.StringUtils;

public class TypeMappingException extends RuntimeException {

    private static final long serialVersionUID = -2977205125815365589L;

    public TypeMappingException(String message) {
        super(message);
    }

    public TypeMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        final String message = super.getMessage();

        if (!StringUtils.isNullOrEmpty(message)) {
            return "Mapping Exception: " + message;
        }

        return null;
    }

}
