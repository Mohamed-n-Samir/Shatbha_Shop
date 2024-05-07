package com.shatbha_shop.shatbha_shop.Exceptions;

import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {

    public ConflictException() {

    }

    public ConflictException(String message) {
        super(message);

    }

    public ConflictException(Throwable cause) {
        super(cause);

    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.CONFLICT;
    }

}
