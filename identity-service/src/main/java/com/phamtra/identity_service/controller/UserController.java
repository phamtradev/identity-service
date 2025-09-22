package com.phamtra.identity_service.controller;

import com.phamtra.identity_service.dto.request.UserDTORequest;
import com.phamtra.identity_service.entity.User;
import com.phamtra.identity_service.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
