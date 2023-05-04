package com.scaler.taskmanager.dtos.requestdtos;

import lombok.Data;

import java.util.Date;

@Data
public class CreateTaskRequestDto {
    String taskName;
    String user;
    Date dueDate;
}
