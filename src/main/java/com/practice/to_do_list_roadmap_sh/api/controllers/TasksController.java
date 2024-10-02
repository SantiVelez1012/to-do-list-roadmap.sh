package com.practice.to_do_list_roadmap_sh.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.to_do_list_roadmap_sh.api.dtos.TaskDTO;
import com.practice.to_do_list_roadmap_sh.api.exceptions.GenericException;
import com.practice.to_do_list_roadmap_sh.api.services.AuthInfoService;
import com.practice.to_do_list_roadmap_sh.api.services.LocalUserService;
import com.practice.to_do_list_roadmap_sh.api.services.TaskService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.Map;


@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    private final TaskService taskService;

    private final LocalUserService localUserService;

    TasksController( TaskService taskService, LocalUserService localUserService ){
        this.taskService = taskService;
        this.localUserService = localUserService;
    }

    @PostMapping()
    public ResponseEntity<TaskDTO> createNewTask(@RequestBody TaskDTO newTask, @RequestHeader Map<String, String> headers) throws GenericException {
        String token = headers.get("authorization");
        String username = localUserService.getUsernameFromToken(token);
        newTask.setOwner(username);
        TaskDTO taskCreated = taskService.createNewTask(newTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }
    
    


}
