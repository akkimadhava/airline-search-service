package com.abn.airline.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * AirlineGlobalExceptionHandler class, This is centralized class to handle all kinds of exceptions
 */
@ControllerAdvice
@Slf4j
public class AirlineGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * this method handle NoDataFoundException exception is thrown when no flights found
     *
     * @param NoDataFoundException
     * @param request
     * @return ResponseEntity<ErrorDetails>
     */
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ErrorDetails> handleNoDataFoundException(NoDataFoundException nfe, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), nfe.getMessage(),
                request.getDescription(false));
        log.error("No data found exception:  {}", nfe.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NO_CONTENT);
    }

    /**
     * this method handle Invalid/BadExceptions exception is thrown when invalid/bad request found
     *
     * @param InvalidRequestException
     * @param request
     * @return ResponseEntity<ErrorDetails>
     */
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorDetails> handleBadExceptions(InvalidRequestException ire, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ire.getMessage(),
                request.getDescription(false));
        log.error("Invalid/Bad request :  {}", ire.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * this method handle ServerExceptions exception is thrown when server exception found
     *
     * @param Exception
     * @param request
     * @return ResponseEntity<ErrorDetails>
     */
    @ExceptionHandler(InternalServerErrorException.class)
    public final ResponseEntity<ErrorDetails> handleServerExceptions(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        log.error("Internal server exception :  {}", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
