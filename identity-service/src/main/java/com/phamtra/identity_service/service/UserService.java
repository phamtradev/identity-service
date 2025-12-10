package com.phamtra.identity_service.service;

import com.phamtra.identity_service.dto.request.UserCreateDTORequest;
import com.phamtra.identity_service.dto.request.UserUpdateDTORequest;
import com.phamtra.identity_service.entity.User;

import java.util.List;

public interface UserService {
    User createUser(UserCreateDTORequest request);

    User updateUser(long id, UserUpdateDTORequest request);

    List<User> getUsers();

    User getUserbyId(long id);

    void deleteUser(long id);

    boolean isUsernameExist(String username);

    User getUserByUsername(String username);
}
