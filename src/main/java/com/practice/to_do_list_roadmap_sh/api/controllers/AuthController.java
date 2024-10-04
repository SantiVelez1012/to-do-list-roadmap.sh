package com.practice.to_do_list_roadmap_sh.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.practice.to_do_list_roadmap_sh.api.dtos.UserCreateDTO;
import com.practice.to_do_list_roadmap_sh.api.dtos.UserLoginDTO;
import com.practice.to_do_list_roadmap_sh.api.exceptions.GenericException;
import com.practice.to_do_list_roadmap_sh.api.services.AuthInfoService;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthInfoService userInfoService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthInfoService userInfoService, PasswordEncoder passwordEncoder) {
        this.userInfoService = userInfoService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserCreateDTO user) throws GenericException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String responseMessage = userInfoService.createUser(user);
        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser( @RequestBody UserLoginDTO user) throws GenericException {
        Map<?, ?> response = userInfoService.loginByUserName(user);
        return ResponseEntity.ok(response);
    }
    
        

}
