package com.phamtra.identity_service.service;

import com.phamtra.identity_service.dto.request.UserCreateDTORequest;
import com.phamtra.identity_service.dto.request.UserUpdateDTORequest;
import com.phamtra.identity_service.entity.User;
import com.phamtra.identity_service.exception.IdInvalidException;
import com.phamtra.identity_service.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public User createUser(UserCreateDTORequest request) {

        User user = modelMapper.map(request, User.class);

        return this.userRepository.save(user);
    }

    public User updateUser(long id, UserUpdateDTORequest request) {
        User user = getUserbyId(id);

        modelMapper.map(request, user);

        return this.userRepository.save(user);
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User getUserbyId(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    public void deleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public boolean isUsernameExist(String username) {
        return this.userRepository.existsByUsername(username);
    }
}
