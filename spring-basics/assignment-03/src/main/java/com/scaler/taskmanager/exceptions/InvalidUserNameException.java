package com.scaler.taskmanager.exceptions;

public class InvalidUserNameException extends IllegalArgumentException{
    public InvalidUserNameException (String errMsg) {
        super(errMsg);
    }
}
