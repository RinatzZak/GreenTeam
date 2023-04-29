package org.aston.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorType {
    CURRENCY_NOT_FOUND(HttpStatus.NOT_FOUND),
    BAD_CHAR_CODE(HttpStatus.BAD_REQUEST);

    private final HttpStatus status;
}
