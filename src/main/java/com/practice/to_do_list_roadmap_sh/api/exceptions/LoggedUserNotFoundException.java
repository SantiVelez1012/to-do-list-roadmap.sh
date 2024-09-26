package com.practice.to_do_list_roadmap_sh.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(HttpStatus.NOT_FOUND)
public class LoggedUserNotFoundException extends RuntimeException {
    public LoggedUserNotFoundException(String message) {
        super(message);
    }
}
