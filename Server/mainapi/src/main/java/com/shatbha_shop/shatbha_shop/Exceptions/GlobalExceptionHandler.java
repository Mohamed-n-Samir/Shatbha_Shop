package com.shatbha_shop.shatbha_shop.Exceptions;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.shatbha_shop.shatbha_shop.Errors.ErrorBody;
import com.shatbha_shop.shatbha_shop.Errors.ValidationError;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorBody> handleCustomException(final BaseException ex, WebRequest request) {

        ErrorBody body = new ErrorBody(ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<ErrorBody>(body, ex.getStatusCode());
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ValidationError validationError = new ValidationError();
        validationError.setUri(request.getDescription(false));

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError f : fieldErrors) {
            validationError.addError(f.getDefaultMessage());
        }

        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
        String errorMessage = "JWT token expired. Please authenticate again.";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }
}
