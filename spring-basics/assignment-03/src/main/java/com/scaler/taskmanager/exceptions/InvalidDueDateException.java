package com.scaler.taskmanager.exceptions;

public class InvalidDueDateException extends IllegalArgumentException{
    public InvalidDueDateException (String errMsg) {
        super(errMsg);
    }
}
