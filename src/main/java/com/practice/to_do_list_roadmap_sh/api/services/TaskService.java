package com.practice.to_do_list_roadmap_sh.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public TaskDTO createNewTask(TaskDTO task, String token){

        Task newTask = new Task();
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        
        String username = localUserService.getUsernameFromToken(token);
        Optional<LocalUser> taskOwner = localUserService.getUserByUsername(username);
        if(!taskOwner.isPresent()) throw new GenericException(Causes.USER_NOT_FOUND);
        newTask.setUser(taskOwner.get());

        Task taskCreated = taskRepository.save(newTask);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle(taskCreated.getTitle());
        taskDTO.setDescription(taskCreated.getDescription());
        taskDTO.setOwner(taskCreated.getUser().getUsername());
        return taskDTO;

    }

    public Page<Task> getAllTasks(int page, int size, String token){

        String username = localUserService.getUsernameFromToken(token);
        PageRequest pageRequest = PageRequest.of(page, size);
        return taskRepository.findByUserUsername(username, pageRequest);

    }

    public TaskDTO updateTask(Long id, TaskDTO task, String token){

        String username = localUserService.getUsernameFromToken(token);
        Optional<LocalUser> taskOwner = localUserService.getUserByUsername(username);
        if(!taskOwner.isPresent()) throw new GenericException(Causes.USER_NOT_FOUND);

        Optional<Task> taskToUpdate = taskRepository.findById(id);
        if(!taskToUpdate.isPresent()) throw new GenericException(Causes.TASK_NOT_FOUND);

        if(!taskToUpdate.get().getUser().getUsername().equals(username)) throw new GenericException(Causes.USER_DOES_NOT_OWN_THIS_TASK);

        Task taskUpdated = taskToUpdate.get();
        taskUpdated.setTitle(task.getTitle());
        taskUpdated.setDescription(task.getDescription());

        Task taskSaved = taskRepository.save(taskUpdated);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle(taskSaved.getTitle());
        taskDTO.setDescription(taskSaved.getDescription());
        taskDTO.setOwner(taskSaved.getUser().getUsername());
        return taskDTO;

    }
}
