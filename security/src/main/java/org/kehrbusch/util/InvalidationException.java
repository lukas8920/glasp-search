package org.kehrbusch.util;

import org.springframework.http.HttpStatus;

public class InvalidationException extends Exception{
    private HttpStatus httpStatus;

    public InvalidationException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus(){
        return this.httpStatus;
    }
}
