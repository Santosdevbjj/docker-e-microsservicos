package com.faang.users.controller;

import com.faang.users.model.User;
import com.faang.users.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getAll() {
        return service.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return service.save(user);
    }
}
