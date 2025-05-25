package com.example.eventplatform.controller;

import com.example.eventplatform.repository.User;
import com.example.eventplatform.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable Integer id) { // Изменили Long на Integer
        userService.delete(id);
    }

    @PutMapping(path = "{id}")
    public void update(@PathVariable Integer id, // Изменили Long на Integer
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) String surname,
                       @RequestParam(required = false) String number,
                       @RequestParam(required = false) String email) {
        userService.update(id, name, surname, number, email);
    }

    @GetMapping("/me")
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }
}