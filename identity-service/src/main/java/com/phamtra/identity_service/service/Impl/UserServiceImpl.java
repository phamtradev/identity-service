package com.phamtra.identity_service.service.Impl;

import com.phamtra.identity_service.dto.request.UserCreateDTORequest;
import com.phamtra.identity_service.dto.request.UserUpdateDTORequest;
import com.phamtra.identity_service.entity.User;
import com.phamtra.identity_service.mapper.UserMapper;
import com.phamtra.identity_service.repository.UserRepository;
import com.phamtra.identity_service.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User createUser(UserCreateDTORequest request) {
        User user = userMapper.toUser(request);
        return this.userRepository.save(user);
    }

    @Override
    public User updateUser(long id, UserUpdateDTORequest request) {
        User user = getUserbyId(id);
        userMapper.updateUser(user, request);
        return this.userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserbyId(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.orElse(null);
    }

    @Override
    public void deleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public boolean isUsernameExist(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
