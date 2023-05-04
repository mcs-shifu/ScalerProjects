package com.scaler.taskmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;

// ---------------------------------------------------------------
//                  Handle Exceptions for our TaskManager
// ---------------------------------------------------------------


// References:
// https://mkyong.com/spring-boot/spring-rest-error-handling-example/
// https://www.baeldung.com/spring-exceptions-json

@RestControllerAdvice
public class TaskManagerExceptionHandler {
	// Done: Assignment 03 v2.0:
	// TODO 08: in error responses send the error message in a JSON object

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<TaskManagerErrorResponse> handleTaskNotFoundException (TaskNotFoundException e) {
        TaskManagerErrorResponse response = new TaskManagerErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setError(e.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidUserNameException.class)
    public ResponseEntity<TaskManagerErrorResponse> handleInvalidUserNameException (InvalidUserNameException e) {
        TaskManagerErrorResponse response = new TaskManagerErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setError(e.getLocalizedMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


	// Done: Assignment 03 v2.0:
	// TODO 07: also handle IllegalArgumentException (dueDate, taskName etc)
    @ExceptionHandler(InvalidDueDateException.class)
    public ResponseEntity<TaskManagerErrorResponse> handleInvalidDueDateException (InvalidDueDateException e) {
        TaskManagerErrorResponse response = new TaskManagerErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setError(e.getLocalizedMessage()  );
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(InvalidTaskNameException.class)
    public ResponseEntity<TaskManagerErrorResponse> handleInvalidTaskNameException (InvalidTaskNameException e) {
        TaskManagerErrorResponse response = new TaskManagerErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setError(e.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
