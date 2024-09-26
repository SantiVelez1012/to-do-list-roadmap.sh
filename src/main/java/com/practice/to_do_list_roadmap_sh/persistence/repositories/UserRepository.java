package com.practice.to_do_list_roadmap_sh.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.to_do_list_roadmap_sh.persistence.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByName(String name);
    public Optional<User> findByEmail(String email);

}
