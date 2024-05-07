package com.orderservice.orderservice.Exceptions;

import org.springframework.http.HttpStatus;

public class WrongTokenException extends BaseException{

    public WrongTokenException() {

    }

    public WrongTokenException(String message) {
        super(message);

    }

    public WrongTokenException(Throwable cause) {
        super(cause);

    }

    public WrongTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
    
}

