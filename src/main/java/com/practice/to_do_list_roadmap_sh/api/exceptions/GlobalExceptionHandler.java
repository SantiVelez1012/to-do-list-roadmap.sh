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
        HttpStatus status = HttpStatus.OK; // set the default status
        Causes cause = exception.cause;

        switch (cause) {
            case TASK_NOT_FOUND -> status = HttpStatus.NOT_FOUND;
            case USER_NOT_FOUND -> status = HttpStatus.NOT_FOUND;
            case USER_ALREADY_EXISTS -> status = HttpStatus.BAD_REQUEST;
        }

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                exception.getMessage(),
                exception.getCause().getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

}
