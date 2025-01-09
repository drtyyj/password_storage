package com.example.password_storage.controller;

import com.example.password_storage.model.User;
import com.example.password_storage.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Iterable<User> findAllUsers() {
        return this.userService.findAllUsers();
    }

    @PostMapping("/users")
    public User addOneEmployee(@RequestBody User user, @RequestParam("safetyLevel") Integer safetyLevel) {
        return this.userService.addOneUser(user, safetyLevel);
    }

    @DeleteMapping("/users")
    public void deleteAllUsers() { this.userService.deleteAllUsers(); }
}