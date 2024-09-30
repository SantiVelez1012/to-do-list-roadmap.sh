package com.practice.to_do_list_roadmap_sh.api.services;

import org.springframework.stereotype.Service;

import com.practice.to_do_list_roadmap_sh.api.dtos.TaskDTO;
import com.practice.to_do_list_roadmap_sh.persistence.entities.Task;
import com.practice.to_do_list_roadmap_sh.persistence.repositories.TaskRepository;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    private LocalUserService localUserService;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createNewTask(TaskDTO task){
        
        Task newTask = new Task();
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());

        return taskRepository.save(newTask);

    }
}
