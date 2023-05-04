package com.scaler.taskmanager.controllers;

import com.scaler.taskmanager.dtos.requestdtos.CreateTaskRequestDto;
import com.scaler.taskmanager.dtos.requestdtos.UpdateTaskRequestDto;
import com.scaler.taskmanager.dtos.responsedtos.CreateTaskResponseDto;
import com.scaler.taskmanager.dtos.responsedtos.DeleteTaskResponseDto;
import com.scaler.taskmanager.dtos.responsedtos.GetTaskResponseDto;
import com.scaler.taskmanager.dtos.responsedtos.UpdateTaskResponseDto;
import com.scaler.taskmanager.entities.Task;
import com.scaler.taskmanager.exceptions.TaskNotFoundException;
import com.scaler.taskmanager.services.TasksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/tasks")
public class TasksController {
    // ---------------------------------------------------------------
    // private attributes
    // ---------------------------------------------------------------
    private final TasksService ts;


    // ---------------------------------------------------------------
    //                  CTOR
    // ---------------------------------------------------------------
    public TasksController (TasksService ts) {  // using dependency injection
        this.ts = ts;
    }

    // ---------------------------------------------------------------
    //                  DELETE
    // ---------------------------------------------------------------
	// Done: Assignment 03 v1.0: Delete a task
	//					Respond with 404 if task is not found
	// Done: Assignment 03 v2.0: TODO 02: implement DELETE task

    @DeleteMapping("/{id}")
    ResponseEntity<DeleteTaskResponseDto> deleteTask (@PathVariable("id") Integer id) {
        // Done: TODO 02: implement DELETE task
        var task = ts.deleteTask(id);
        DeleteTaskResponseDto responseDto = new DeleteTaskResponseDto(task);
        return ResponseEntity.ok(responseDto);
    }

    // Done: Assignment 03 v1.0, Bonus Task 2:
	// Bulk Delete tasks which are completed to be implemented
	// DELETE /tasks?completed=true should delete all completed tasks
	//
	// Added extra feature (or bug?)
	// DELETE /tasks?completed=false will delete all incomplete tasks


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("")
    ResponseEntity<String> deleteCompletedTasks (
            @RequestParam(value = "completed", required = true) Boolean completed) {
        // Done: TODO: Assignment 03, v1.0: implement DELETE /tasks?completed=true
        Integer numDeleted = ts.deleteTasks(completed);
        return ResponseEntity.ok("Number of tasks deleted: " + numDeleted);
    }
    // ---------------------------------------------------------------
    //                  GET
    // ---------------------------------------------------------------
	// Done: Assignment 03 v1.0: List all tasks
    // Done: Assignment 03 v2.0: TODO 03: create a TaskResponseDTO and do not return Task directly
    @GetMapping("")
    ResponseEntity<List<GetTaskResponseDto>> getAllTasks (
            @RequestParam(value = "beforeDate", required = false) Date beforeDate,
            @RequestParam(value = "afterDate", required = false) Date afterDate,
            @RequestParam(value = "completed", required = false) Boolean completed) {

		// Done: Assignment 03: Bonus Task 1: (i) and (ii)
		// (i) GET /tasks?completed=true should return all completed tasks
		// (ii) GET /tasks?completed=false should return all incomplete tasks

        var filter = TasksService.TaskFilter.fromQueryParams(beforeDate, afterDate, completed);
        var tasks = ts.getAllTasks(filter);

		// !!! NOT YET DONE: Assignment 03: Bonus Task 1: (iii) and (iv) !!!
    	// (iii): GET /tasks?sort=dateDesc should return all tasks sorted in descending order by dueDate
		// (iv): GET /tasks?sort=dateAsc should return all tasks sorted in ascending order by dueDate

        // convert tasks to a list of GetTaskResponseDtos
        List<GetTaskResponseDto> result = new ArrayList<>();
        for (Task task : tasks) {   // Surely there's a smarter way to do this????
            // TODO: we may want to use some kind of prototype/registry/repository here to get the DTOs
            result.add(new GetTaskResponseDto(task));
        }
        return ResponseEntity.ok(result);
    }


	// Done: Assignment 03 v1.0: Fetch a task by ID
	//					Respond with 404 if task is not found
    @GetMapping("/{id}")
    ResponseEntity<GetTaskResponseDto> getTaskById (@PathVariable("id") Integer id) throws TaskNotFoundException {
        var task = ts.getTaskById(id);

        // convert task to GetTaskResponseDto
        GetTaskResponseDto responseDto = new GetTaskResponseDto(task);
        // TODO: we may want to use some kind of prototype/registry here to get the DTOs

        return ResponseEntity.ok(responseDto);
    }

    // ---------------------------------------------------------------
    //                  PATCH
    // ---------------------------------------------------------------
	// Done: Assignment 03 v1.0: Update a task (status and due date can be updated)
	//					Respond with 404 if task is not found
	// Done: Assignment 03 v2.0: TODO 01: implement PATCH task
    @PatchMapping("/{id}")
    ResponseEntity<UpdateTaskResponseDto> updateTask (
            @PathVariable("id") Integer id,
            @RequestBody UpdateTaskRequestDto utrd) {
        // Done: TODO 01: implement PATCH task
        var task = ts.updateTask(id, utrd.getDueDate(), utrd.getCompleted());
        UpdateTaskResponseDto responseDto = new UpdateTaskResponseDto(task);
        return ResponseEntity.ok(responseDto);
    }


    // ---------------------------------------------------------------
    //                  POST
    // ---------------------------------------------------------------
	// Done: Assignment 03 v1.0: Create a Task
	// 			Error if name is missing
	//			Error if due date is missing or invalid (before today)

    @PostMapping("")
    ResponseEntity<CreateTaskResponseDto> createTask (@RequestBody CreateTaskRequestDto ctrd) {
        var task = ts.createTask(ctrd.getUser(), ctrd.getTaskName(), ctrd.getDueDate());
        CreateTaskResponseDto responseDto = new CreateTaskResponseDto(task);
        // TODO: we may want to use some kind of prototype/registry here to get the DTOs

        return ResponseEntity.ok(responseDto);
    }


    // ---------------------------------------------------------------
    //                  PUT
    // ---------------------------------------------------------------
    @PutMapping("/{id}")
    ResponseEntity<CreateTaskResponseDto> createTask (
            @PathVariable("id") Integer id,
            @RequestBody CreateTaskRequestDto ctrd) {
        // This is not part of the assignment TODO but I wanted to try it out.
        var task = ts.createTask(id, ctrd.getDueDate());
        CreateTaskResponseDto responseDto = new CreateTaskResponseDto(task);
        return ResponseEntity.ok(responseDto);
    }
}
