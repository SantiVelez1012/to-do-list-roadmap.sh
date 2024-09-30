package com.practice.to_do_list_roadmap_sh.api.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.practice.to_do_list_roadmap_sh.config.JwtService;
import com.practice.to_do_list_roadmap_sh.persistence.entities.LocalUser;
import com.practice.to_do_list_roadmap_sh.persistence.repositories.UserRepository;

@Service
public class LocalUserService {

    private final JwtService jwtService;

    private final UserRepository localUserRepository;

    public LocalUserService(JwtService jwtService, UserRepository localUserRepository) {
        this.jwtService = jwtService;
        this.localUserRepository = localUserRepository;
    }

    public String getUsernameFromToken(String token){
        return jwtService.extractUsername(token);
    }

    public Optional<LocalUser> getUserByUsername(String username){
        return localUserRepository.findByUsername(username);
    }
    


}
