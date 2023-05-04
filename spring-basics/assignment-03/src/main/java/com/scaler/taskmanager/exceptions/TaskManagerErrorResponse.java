package com.scaler.taskmanager.exceptions;

import java.time.LocalDateTime;

public class TaskManagerErrorResponse {
    private LocalDateTime timeStamp;
    private int status;
    private String error;

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public TaskManagerErrorResponse setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public TaskManagerErrorResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getError() {
        return error;
    }

    public TaskManagerErrorResponse setError(String error) {
        this.error = error;
        return this;
    }
}
