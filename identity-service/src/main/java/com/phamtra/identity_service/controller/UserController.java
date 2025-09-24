package com.phamtra.identity_service.controller;

import com.phamtra.identity_service.dto.request.UserCreateDTORequest;
import com.phamtra.identity_service.dto.request.UserUpdateDTORequest;
import com.phamtra.identity_service.entity.User;
import com.phamtra.identity_service.exception.IdInvalidException;
import com.phamtra.identity_service.service.UserService;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateDTORequest request) throws IdInvalidException {
        String hassPassword = this.passwordEncoder.encode(request.getPassword());
        request.setPassword(hassPassword);
        boolean isUsernameExist = this.userService.isUsernameExist(request.getUsername());
        if (isUsernameExist) {
            throw new IdInvalidException("Username " + request.getUsername() + " đã tồn tại, vui lòng sử dụng username khác");
        }
        User user = this.userService.createUser(request);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(this.userService.getUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") long id) {
        User user = this.userService.getUserbyId(id);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody UserUpdateDTORequest request) {
        User user = this.userService.updateUser(id, request);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) throws IdInvalidException {
        User currentUser = this.userService.getUserbyId(id);
        if (currentUser == null) {
            throw new IdInvalidException("User với id: " + id + " không tồn tại");
        }
        this.userService.deleteUser(id);
        return ResponseEntity.ok(null);
    }
}
