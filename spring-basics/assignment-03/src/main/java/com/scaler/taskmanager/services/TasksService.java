package com.scaler.taskmanager.services;

import com.scaler.taskmanager.entities.Task;
import com.scaler.taskmanager.exceptions.InvalidDueDateException;
import com.scaler.taskmanager.exceptions.InvalidTaskNameException;
import com.scaler.taskmanager.exceptions.InvalidUserNameException;
import com.scaler.taskmanager.exceptions.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class TasksService {
    // ---------------------------------------------------------------
    // private attributes
    // ---------------------------------------------------------------
    private List<Task> taskList = new ArrayList<>();
    private Integer id = 0;

    // Done: Assignment 03 v1.0: 
	// TODO 05: generate error for invalid name (less than 5 char, or more than 100 char)
    private static final Integer MinTaskNameLen = 5;
    private static final Integer MaxTaskNameLen = 100;


    // ---------------------------------------------------------------
    // public methods to create, update, and delete tasks
    // ---------------------------------------------------------------
    public Task createTask (String user, String taskName, Date dueDate) {
        // Handle: TODO 04, 05, and 06
        validateDueDate(dueDate);
        validateTaskName(taskName);

        Task task = new Task(id++, user, taskName, dueDate, false);
        // by default, a newly created task is not considered as completed
        // until its status is explicitly updated with a PUT request
        taskList.add(task);
        return task;
    }

    public Task createTask (Integer id, Date dueDate) {
        // To handle PUT Mapping. This isn't part of the TODOs given in the assignment;
        // but I wanted to try it.

        Integer index = getTask(id);
        validateDueDate(dueDate);

        Task oldTask = taskList.get(index);
        Task newTask = new Task(id, oldTask.getUser(), oldTask.getTaskName(), dueDate, oldTask.getCompleted());
        taskList.set(index, newTask);
        return newTask;
    }

    public Task updateTask (Integer id, Date dueDate, Boolean status) {
        Task task = getTaskById(id);
        if (dueDate != null) {
            validateDueDate(dueDate);
            task.setDueDate(dueDate);
        }
        if (status != null) {
            task.setCompleted(status);
        }
        return task;
    }

    public Task deleteTask (Integer id) {
        Task task = getTaskById(id);
        taskList.remove(task);
        return task;
    }

    // Assignment 03: Bonus Task 2: Delete tasks which are completed
    // DELETE /tasks?completed=true should delete all completed tasks
    //
    // Note that deleteTasks can be used to delete all completed tasks
    // or all incompleted tasks.
    // That is, it will also support:
    // DELETE /tasks?completed=false to delete all incomplete tasks
    public Integer deleteTasks (Boolean completed) {
        List<Integer> indicesToDelete = new ArrayList<>();
        for (int i = 0; i < taskList.size(); ++i) {
            if (taskList.get(i).getCompleted() == completed) {
                indicesToDelete.add(i);
            }
        }
        if (indicesToDelete.size() == 0) {
            return 0;
        }
        Collections.sort(indicesToDelete, Collections.reverseOrder());
        for (int i : indicesToDelete) {
            taskList.remove(i);
        }
        return indicesToDelete.size();
    }

    // ---------------------------------------------------------------
    //  public access/search/filter/sort methods
    // ---------------------------------------------------------------
    public List<Task> getAllTasks (TaskFilter filter) {
        if (filter == null) {
            return taskList;
        } else {
            var selectedTasks = taskList.stream().filter(task -> {
                if (filter.beforeDate != null && task.getDueDate().after(filter.beforeDate)) {
                    return false;
                }
                if (filter.afterDate != null && task.getDueDate().before(filter.afterDate)) {
                    return false;
                }
                if (filter.completed != null && task.getCompleted() != filter.completed) {
                    return false;
                }
                return true;
            }).toList();
            return selectedTasks;
        }
    }



    public Task getTaskById (Integer id) {
        for (Task task : taskList) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        throw new TaskNotFoundException(id);
    }

    // ---------------------------------------------------------------
    // miscellaneous private auxiliary methods
    // ---------------------------------------------------------------
    private Integer getTask (Integer id) {
        for (int i = 0;  i < taskList.size();  ++i) {
            if (taskList.get(i).getId().equals(id)) {
                return i;
            }
        }
        throw new TaskNotFoundException(id);
    }

    // ---------------------------------------------------------------
    // private auxiliary methods for validation of inputs
    // ---------------------------------------------------------------
    private void validateUser (String user) {
        if (user == null  ||  user == "") {
            throw new InvalidUserNameException("User names are mandatory.");
        }
    }
    private void validateDueDate (Date dueDate) {
		// Done: Assignment 03 v2.0
		// TODO 04: generate error for invalid dueDate (before today)
		// TODO 06: generate error for invalid dueDate (before today)

        if (dueDate == null) {
            // Note:
            // When we create a new task, dueDate MUST be non-null.
            // However, when we update a task, dueDate may be null.
            // In this case, validateDueDate() is only called by updateTask()
            // if dueDate is non-null.

            throw new InvalidDueDateException("Missing due date. Due date is mandatory when creating tasks");
        } else if (dueDate.before(new Date())) {
            // TODO: we may want to consider using LocalDate instead of new Date()
            //  or a better way of doing this...
            throw new InvalidDueDateException("Due date cannot be in the past: " + dueDate);
        }
    }

    private void validateTaskName (String name) {
		// Done: Assignment 03 v1.0: 
		// TODO 05: generate error for invalid name (less than 5 char, or more than 100 char)
        if (name == null  ||  name == "") {
            throw new InvalidTaskNameException("Task names are mandatory and must be between " + MinTaskNameLen +  " and " + MaxTaskNameLen + " characters in length");
        } else if (name.length() < MinTaskNameLen) {
            throw new InvalidTaskNameException("Task name must be at least " + MinTaskNameLen + " characters long");
        }  else if (name.length() > MaxTaskNameLen) {
            throw new InvalidTaskNameException("Task name can be at most " + MaxTaskNameLen + " characters long");
        }
    }


    // ---------------------------------------------------------------
    // auxiliary classes for filter and sort features
    // ---------------------------------------------------------------
    public static class TaskFilter{
        Date beforeDate;
        Date afterDate;
        Boolean completed;

        // Assignment 03 v1.0:
        // Bonus Tasks
        // 1. Sort and filter functionality for list tasks
        //      (i) GET /tasks?completed=true should return all completed tasks
        //      (ii) GET /tasks?completed=false should return all incomplete tasks
        public static TaskFilter fromQueryParams (Date beforeDate, Date afterDate, Boolean completed) {
            if (beforeDate == null  &&  afterDate == null  &&  completed == null) {
                return null;
            }
            TaskFilter filter = new TaskFilter();
            filter.beforeDate = beforeDate;
            filter.afterDate = afterDate;
            filter.completed = completed;
            return filter;
        }

    }

    // Assignment 03 v1.0:
    // Bonus Tasks
    // 1. Sort and filter functionality for list tasks
    //      (iii) GET /tasks?sort=dateDesc should return all tasks sorted by due date
    //      (iv) GET /tasks?sort=dateAsc should return all tasks sorted by due date
    public static class TaskDateSorter {
        Boolean ascending;

        public static TaskDateSorter fromQueryParams(String order) {
            if (order == null  ||  order == "") {
                return null;
            }
            if (order != "dateAsc"  &&  order != "dateDesc") {
                return null;
            }

            TaskDateSorter sorter = new TaskDateSorter();

            if (order.equals("dateAsc")) {
                sorter.ascending = true;
            } else {
                // order.equals("dateDesc")
                sorter.ascending = false;
            }
            return sorter;
        }
    }
}
