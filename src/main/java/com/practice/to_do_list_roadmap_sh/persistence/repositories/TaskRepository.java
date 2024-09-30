package com.practice.to_do_list_roadmap_sh.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.to_do_list_roadmap_sh.persistence.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
