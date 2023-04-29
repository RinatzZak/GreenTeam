package org.aston.util;

import lombok.extern.slf4j.Slf4j;
import org.aston.util.exception.BadRequestForCharCodeException;
import org.aston.util.exception.CurrencyNotFoundException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class GlobalHandlerException {

    /**
     * Method that handles exception when entity wasn't found
     *
     * @param req request {@link HttpServletRequest}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(CurrencyNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleCurrencyNotFoundException(HttpServletRequest req,
                                                                     CurrencyNotFoundException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.CURRENCY_NOT_FOUND);
    }

    /**
     * Method that handles exception when charCode not valid
     *
     * @param req request {@link HttpServletRequest}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(CurrencyNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleBadRequestForCharCodeException(HttpServletRequest req,
                                                                     BadRequestForCharCodeException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.BAD_CHAR_CODE);
    }

    private ResponseEntity<ErrorInfo> logAndGetErrorInfo(HttpServletRequest req, Exception e, ErrorType errorType) {
        Throwable rootCause = Optional.ofNullable(NestedExceptionUtils.getRootCause(e)).orElse(e);
        log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        return ResponseEntity
                .status(errorType.getStatus())
                .body(new ErrorInfo("/currency" + req.getRequestURI(), errorType, e.getMessage(), LocalDateTime.now()));
    }
}
