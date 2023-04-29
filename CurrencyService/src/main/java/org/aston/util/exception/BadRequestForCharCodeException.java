package org.aston.util.exception;

public class BadRequestForCharCodeException extends RuntimeException {
    public BadRequestForCharCodeException(Throwable cause) {
        super(cause);
    }

    public BadRequestForCharCodeException(String message) {
        super(message);
    }
}
