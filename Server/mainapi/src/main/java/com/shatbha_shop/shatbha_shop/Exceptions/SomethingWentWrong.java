package com.shatbha_shop.shatbha_shop.Exceptions;

import org.springframework.http.HttpStatus;

public class SomethingWentWrong extends BaseException{
    public SomethingWentWrong() {

    }

    public SomethingWentWrong(String message) {
        super(message);

    }

    public SomethingWentWrong(Throwable cause) {
        super(cause);

    }

    public SomethingWentWrong(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}



