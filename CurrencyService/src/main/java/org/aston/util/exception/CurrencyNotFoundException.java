package org.aston.util.exception;

public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyNotFoundException() {
    }
}
