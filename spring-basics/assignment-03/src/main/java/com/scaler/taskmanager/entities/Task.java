package com.scaler.taskmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class Task {
    Integer id;
    String  user;   // we may want to associate tasks with users later
    String  taskName;

    // the following two can be changed in an update request; hence the need for setters
    @Setter
    Date dueDate;

    @Setter
    Boolean completed;
}
