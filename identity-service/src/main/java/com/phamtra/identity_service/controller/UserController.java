package com.phamtra.identity_service.controller;

import com.phamtra.identity_service.dto.request.UserDTORequest;
import com.phamtra.identity_service.entity.User;
import com.phamtra.identity_service.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody UserDTORequest request) {
        return this.userService.createUser(request);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") long id) {
        return this.userService.getUserbyId(id);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable("id") long id, @RequestBody UserDTORequest request) {
        return this.userService.updateUser(id, request);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        this.userService.deleteUser(id);
        return "success!";
    }
}
