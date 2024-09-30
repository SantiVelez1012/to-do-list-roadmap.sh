package com.practice.to_do_list_roadmap_sh.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.to_do_list_roadmap_sh.persistence.entities.LocalUser;

@Repository
public interface UserRepository extends JpaRepository<LocalUser, Long> {

    public Optional<LocalUser> findByUsername(String username);
    public Optional<LocalUser> findByEmail(String email);

}
