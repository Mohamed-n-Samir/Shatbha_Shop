package com.orderservice.orderservice.Exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException{

    public NotFoundException() {

    }

    public NotFoundException(String message) {
        super(message);

    }

    public NotFoundException(Throwable cause) {
        super(cause);

    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
    
}
