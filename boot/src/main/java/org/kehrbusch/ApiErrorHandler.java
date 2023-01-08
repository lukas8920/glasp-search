package org.kehrbusch;

import org.kehrbusch.entities.BadRequestException;
import org.kehrbusch.exceptions.EmptyResultException;
import org.kehrbusch.exceptions.InputException;
import org.kehrbusch.exceptions.InternalServerError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({InternalServerError.class, InputException.class, EmptyResultException.class, BadRequestException.class})
    public ResponseEntity<String> handleCustomRequest(Exception e, WebRequest r){
        if (e instanceof InternalServerError){
            logger.warn("Throw exception of class " + e.getClass());
            return ResponseEntity.status(500).body(e.getMessage());
        } else if (e instanceof InputException){
            logger.warn("Throw exception of class " + e.getClass());
            return ResponseEntity.status(400).body(e.getMessage());
        } else if (e instanceof EmptyResultException){
            logger.warn("Throw exception of class " + e.getClass());
            return ResponseEntity.status(404).body(e.getMessage());
        } else if (e instanceof BadRequestException){
            logger.warn("Throw exception of class " + e.getClass());
            return ResponseEntity.status(500).body(e.getMessage());
        }
        return null;
    }
}
