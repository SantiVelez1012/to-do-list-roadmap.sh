package com.practice.to_do_list_roadmap_sh.api.services;

import org.springframework.stereotype.Service;

import com.practice.to_do_list_roadmap_sh.config.JwtService;

@Service
public class LocalUserService {

    private JwtService jwtService;

    public LocalUserService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String getUsername(String token){
        return jwtService.extractUsername(token);
    }

}
