package com.practice.to_do_list_roadmap_sh.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> handleBlogPostExceptions(GenericException exception, WebRequest request) {
        HttpStatus status = HttpStatus.OK;
        Causes cause = exception.cause;

        switch (cause) {
            case TASK_NOT_FOUND -> status = HttpStatus.BAD_REQUEST;
            case USER_NOT_FOUND -> status = HttpStatus.BAD_REQUEST;
            case USER_ALREADY_EXISTS -> status = HttpStatus.BAD_REQUEST;
            case USER_DOES_NOT_OWN_THIS_TASK -> status = HttpStatus.BAD_REQUEST;
            default -> status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                exception.getMessage(),
                exception.cause.label);
        return new ResponseEntity<>(errorResponse, status);
    }

}
