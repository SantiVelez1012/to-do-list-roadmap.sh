package com.practice.to_do_list_roadmap_sh.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.practice.to_do_list_roadmap_sh.api.dtos.UserCreateDTO;
import com.practice.to_do_list_roadmap_sh.api.services.UserInfoService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/auth")
public class AuthController {


    private final UserInfoService userInfoService;

    private final PasswordEncoder passwordEncoder;

    // private Logger logger;

    public AuthController(UserInfoService userInfoService, PasswordEncoder passwordEncoder) {
        this.userInfoService = userInfoService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserCreateDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // logger.info("User created: " + user);
        System.out.println("User created: " + user);
        String responseMessage = userInfoService.createUser(user);
        return ResponseEntity.ok(responseMessage);
    }
    
        

}
