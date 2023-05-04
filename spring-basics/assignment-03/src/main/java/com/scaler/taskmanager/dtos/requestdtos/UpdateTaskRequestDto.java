package com.scaler.taskmanager.dtos.requestdtos;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateTaskRequestDto {
    Date dueDate;
    Boolean completed = false;
}
