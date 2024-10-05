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
        
        String username = getUsernameFromToken(token);
        LocalUser taskOwner = validateIfUserExists(username);
        newTask.setUser(taskOwner);

        Task taskCreated = taskRepository.save(newTask);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle(taskCreated.getTitle());
        taskDTO.setDescription(taskCreated.getDescription());
        taskDTO.setOwner(taskCreated.getUser().getUsername());
        return taskDTO;

    }

    public Page<Task> getAllTasks(int page, int size, String token){

        String username = getUsernameFromToken(token);
        PageRequest pageRequest = PageRequest.of(page, size);
        return taskRepository.findByUserUsername(username, pageRequest);

    }

    public TaskDTO updateTask(Long id, TaskDTO task, String token){

        String username = getUsernameFromToken(token);
        validateIfUserExists(username);

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

    public TaskDTO deleteTask(Long id, String token){

        String username = getUsernameFromToken(token);
        validateIfUserExists(username);
        Optional<Task> taskToDelete = taskRepository.findById(id);
        if(!taskToDelete.isPresent()) throw new GenericException(Causes.TASK_NOT_FOUND);

        if(!taskToDelete.get().getUser().getUsername().equals(username)) throw new GenericException(Causes.USER_DOES_NOT_OWN_THIS_TASK);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle(taskToDelete.get().getTitle());
        taskDTO.setDescription(taskToDelete.get().getDescription());
        taskDTO.setOwner(taskToDelete.get().getUser().getUsername());

        taskRepository.delete(taskToDelete.get());

        return taskDTO;

    }

    private LocalUser validateIfUserExists(String username){

        Optional<LocalUser> taskOwner = localUserService.getUserByUsername(username);
        if(!taskOwner.isPresent()) throw new GenericException(Causes.USER_NOT_FOUND);

        return taskOwner.get();

    }


    private String getUsernameFromToken(String token){
        return localUserService.getUsernameFromToken(token);
    }
}
