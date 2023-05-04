package com.scaler.taskmanager.exceptions;

public class TaskNotFoundException extends IllegalStateException{
    public TaskNotFoundException (Integer id) {
        super("Task ID: " + id + " not found.");
    }
}
