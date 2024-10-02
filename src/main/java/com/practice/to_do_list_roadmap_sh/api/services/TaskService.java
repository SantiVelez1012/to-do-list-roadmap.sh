package com.practice.to_do_list_roadmap_sh.api.services;

import org.springframework.stereotype.Service;

import java.util.Optional;
import com.practice.to_do_list_roadmap_sh.api.dtos.TaskDTO;
import com.practice.to_do_list_roadmap_sh.api.exceptions.Causes;
import com.practice.to_do_list_roadmap_sh.api.exceptions.GenericException;
import com.practice.to_do_list_roadmap_sh.persistence.entities.LocalUser;
import com.practice.to_do_list_roadmap_sh.persistence.entities.Task;
import com.practice.to_do_list_roadmap_sh.persistence.repositories.TaskRepository;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    private LocalUserService localUserService;

    public TaskService(TaskRepository taskRepository, LocalUserService localUserService) {
        this.taskRepository = taskRepository;
        this.localUserService = localUserService;
    }

    public TaskDTO createNewTask(TaskDTO task){

        Task newTask = new Task();
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());

        Optional<LocalUser> taskOwner = localUserService.getUserByUsername(task.getOwner());
        System.out.println(taskOwner.get().toString());
        if(!taskOwner.isPresent()) throw new GenericException(Causes.USER_NOT_FOUND);
        newTask.setUser(taskOwner.get());

        Task taskCreated = taskRepository.save(newTask);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle(taskCreated.getTitle());
        taskDTO.setDescription(taskCreated.getDescription());
        taskDTO.setOwner(taskCreated.getUser().getUsername());
        return taskDTO;

    }
}
