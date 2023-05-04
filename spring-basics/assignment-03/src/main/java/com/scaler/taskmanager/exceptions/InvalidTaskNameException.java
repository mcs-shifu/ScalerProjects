package com.scaler.taskmanager.exceptions;

public class InvalidTaskNameException extends IllegalArgumentException{
    public InvalidTaskNameException (String errMsg) {
        super(errMsg);
    }
}
