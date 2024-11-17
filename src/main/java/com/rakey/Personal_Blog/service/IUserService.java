package com.rakey.Personal_Blog.service;

import com.rakey.Personal_Blog.dto.UpdateUserRequest;
import com.rakey.Personal_Blog.dto.UserDto;
import com.rakey.Personal_Blog.dto.UserRequest;
import com.rakey.Personal_Blog.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IUserService {

    User CreateUser(UserRequest user);
    User getUser(Long id);
    List<User> getAllUsers();

    User updateUser(UpdateUserRequest request, Long id);

    void DeleteUser(Long id);

    UserDto convertUserToDto(User user);

    List<UserDto> getConvertedUsers(List<User> users);

    User getUserByUsername(HttpServletRequest request);
}
