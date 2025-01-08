package com.example.password_storage.controller;

import com.example.password_storage.model.User;
import com.example.password_storage.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public Iterable<User> findAllEmployees() {
        return this.userRepository.findAll();
    }

    @PostMapping("/users")
    public User addOneEmployee(@RequestBody User user) {
        return this.userRepository.save(user);
    }

    @DeleteMapping("/users")
    public void deleteOneEmployee(@RequestBody String username) { this.userRepository.deleteById(username); }
}