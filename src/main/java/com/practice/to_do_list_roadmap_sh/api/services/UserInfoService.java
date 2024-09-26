package com.practice.to_do_list_roadmap_sh.api.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import com.practice.to_do_list_roadmap_sh.api.dtos.UserCreateDTO;
import com.practice.to_do_list_roadmap_sh.api.dtos.UserLoginDTO;
import com.practice.to_do_list_roadmap_sh.api.exceptions.LoggedUserNotFoundException;
import com.practice.to_do_list_roadmap_sh.config.JwtService;
import com.practice.to_do_list_roadmap_sh.persistence.entities.LocalUser;
import com.practice.to_do_list_roadmap_sh.persistence.repositories.UserRepository;
import org.springframework.context.annotation.Lazy;


@Service
public class UserInfoService implements UserDetailsService {

    @Value("{jwt.secret}")
    private String JWT_SECRET;

    private JwtService jwtService;

    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    public UserInfoService(@Lazy JwtService jwtService, UserRepository userRepository,
            @Lazy AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public Optional<LocalUser> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public String createUser(UserCreateDTO user) {

        LocalUser newUser = new LocalUser();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        Optional<LocalUser> existingEmail = userRepository.findByEmail(user.getEmail());

        Optional<LocalUser> existingUser = userRepository.findByUsername(user.getUsername());
        System.out.println("User created: " + user);
        if (existingEmail.isPresent())
            return "This email already exists, please use other";
        if (existingUser.isPresent())
            return "This username already exists, please use other";

        LocalUser createdUser = userRepository.save(newUser);

        return createdUser != null ? "User created successfully" : "Failed on the user creation";

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LocalUser> user = userRepository.findByUsername(username);

        if (!user.isPresent())
            throw new UsernameNotFoundException("User not found");

        return new User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
    }

    public String login(UserCreateDTO user) {
        Optional<LocalUser> existingUser = userRepository.findByUsername(user.getUsername());

        if (!existingUser.isPresent())
            return "User not found";

        if (!existingUser.get().getPassword().equals(user.getPassword()))
            return "Invalid password";

        UserLoginDTO userLogged = new UserLoginDTO(user.getEmail(), user.getPassword());

        return jwtService.generateToken(userLogged);
    }

    public Map<String, String> loginByUserName(UserLoginDTO credentials) throws LoggedUserNotFoundException {
        String username = credentials.getUserName();
        String password = credentials.getPassword();

        Optional<LocalUser> user = userRepository.findByUsername(username);

        if (!user.isPresent()) {
            throw new LoggedUserNotFoundException("Usuario no encontrado en la base de datos");
        } else {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            String token = jwtService.generateToken(credentials);

            Map<String, String> response = new HashMap<>();

            response.put("Token : ", token);

            return response;
        }
    }

}
