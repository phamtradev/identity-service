package com.phamtra.identity_service.service;

import com.phamtra.identity_service.dto.request.UserDTORequest;
import com.phamtra.identity_service.entity.User;
import com.phamtra.identity_service.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserDTORequest request) {
        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setDob(request.getDob());

        return this.userRepository.save(user);
    }
}
