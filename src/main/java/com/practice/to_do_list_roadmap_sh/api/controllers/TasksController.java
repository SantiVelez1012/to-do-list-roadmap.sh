package com.practice.to_do_list_roadmap_sh.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.to_do_list_roadmap_sh.api.dtos.TaskDTO;
import com.practice.to_do_list_roadmap_sh.api.exceptions.GenericException;
import com.practice.to_do_list_roadmap_sh.api.services.TaskService;
import com.practice.to_do_list_roadmap_sh.persistence.entities.Task;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.Map;


@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    private final TaskService taskService;

    TasksController( TaskService taskService ){
        this.taskService = taskService;
    }

    @PostMapping()
    public ResponseEntity<TaskDTO> createNewTask(@RequestBody TaskDTO newTask, @RequestHeader Map<String, String> headers) throws GenericException {
        String token = headers.get("authorization");
        TaskDTO taskCreated = taskService.createNewTask(newTask, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @GetMapping()
    public ResponseEntity<Page<Task>> createNewTask(@RequestParam int page, @RequestParam int size, @RequestHeader Map<String, String> headers) throws GenericException {
        String token = headers.get("authorization");
        Page<Task> taskList = taskService.getAllTasks(page, size, token);
        return ResponseEntity.status(HttpStatus.OK).body(taskList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO task, @RequestHeader Map<String, String> headers) throws GenericException {
        String token = headers.get("authorization");
        TaskDTO updatedTask = taskService.updateTask(id, task, token);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskDTO> deleteTask (@PathVariable Long id, @RequestHeader Map<String, String> headers) throws GenericException {
        String token = headers.get("authorization");
        TaskDTO deletedTask = taskService.deleteTask(id, token);
        return ResponseEntity.status(HttpStatus.OK).body(deletedTask);
    }
}
