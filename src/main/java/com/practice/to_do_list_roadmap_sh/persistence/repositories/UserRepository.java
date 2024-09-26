package com.practice.to_do_list_roadmap_sh.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.to_do_list_roadmap_sh.persistence.entities.LocalUser;

public interface UserRepository extends JpaRepository<LocalUser, Long> {

    public Optional<LocalUser> findByName(String name);
    public Optional<LocalUser> findByEmail(String email);

}
