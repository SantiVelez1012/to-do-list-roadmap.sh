package com.practice.to_do_list_roadmap_sh.api.exceptions;

import java.time.LocalDateTime;

public class ErrorResponse {

    public ErrorResponse(LocalDateTime timeStamp, int status, String error, String cause) {
        this.timeStamp = timeStamp;
        this.status = status;
        this.error = error;
        this.cause = cause;
    }

    private LocalDateTime timeStamp;
    private int status;
    private String error;
    private String cause;
    
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public String getCause() {
        return cause;
    }
    public void setCause(String cause) {
        this.cause = cause;
    }

    
}
