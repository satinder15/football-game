package com.sapient.football.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler({BadRequestException.class})
  public ResponseEntity<Error> handleBadRequestException(Exception ex) {
    log.error("Exception caught: ", ex);
    return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST, ex.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({Exception.class, HttpClientErrorException.class})
  public ResponseEntity<Error> handleException(Exception ex) {
    log.error("Exception caught: ", ex);
    return new ResponseEntity<>(new Error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

}

