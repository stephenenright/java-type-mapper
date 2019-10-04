package com.stephenenright.typemapper.internal.common.error;

import java.util.Objects;

public class ErrorDetail {

    private final Throwable cause;
    private final String message;

    public ErrorDetail(String message) {
      this(message, null);
    }

    public ErrorDetail(String message, Throwable cause) {
      this.message = message;
      this.cause = cause;
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof ErrorDetail)) {
          return false;
      }
      
      ErrorDetail e = (ErrorDetail) o;
      return Objects.equals(message, message) && Objects.equals(cause, e.cause);
    }

 
    public Throwable getCause() {
      return cause;
    }


    public String getMessage() {
      return message;
    }

    @Override
    public int hashCode() {
      return message.hashCode();
    }

    @Override
    public String toString() {
      return message;
    }
}
