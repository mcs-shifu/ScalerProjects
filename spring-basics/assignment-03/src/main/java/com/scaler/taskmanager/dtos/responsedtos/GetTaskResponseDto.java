package com.scaler.taskmanager.dtos.responsedtos;

import com.scaler.taskmanager.entities.Task;
import lombok.Data;

import java.util.Date;

@Data
public class GetTaskResponseDto {
    Integer id;
    String  user;
    String  taskName;
    Date dueDate;
    Boolean completed;

    public GetTaskResponseDto (Task task) {
        this.id = task.getId();
        this.user = task.getUser();
        this.taskName = task.getTaskName();
        this.dueDate = task.getDueDate();
        this.completed = task.getCompleted();
    }
}
