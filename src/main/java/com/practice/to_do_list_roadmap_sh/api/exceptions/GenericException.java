package com.practice.to_do_list_roadmap_sh.api.exceptions;

public class GenericException extends RuntimeException{

    final Causes cause;
    
    public GenericException(Causes cause, Throwable message) {
        super(cause.label, message);
        this.cause = cause;
    }
    
    public GenericException(Causes cause) {
        super(cause.label);
        this.cause = cause;
    }


}
