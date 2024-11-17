package com.rakey.Personal_Blog.controller;

import com.rakey.Personal_Blog.dto.ApiResponse;
import com.rakey.Personal_Blog.dto.UpdateUserRequest;
import com.rakey.Personal_Blog.dto.UserDto;
import com.rakey.Personal_Blog.dto.UserRequest;
import com.rakey.Personal_Blog.exception.AlreadyExistsException;
import com.rakey.Personal_Blog.exception.ResourceNotFoundException;
import com.rakey.Personal_Blog.model.User;
import com.rakey.Personal_Blog.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.grammars.hql.HqlParser.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(
            @RequestBody UserRequest request
    ){
        try {
            User user = userService.CreateUser(request);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success",userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(
            @PathVariable Long userId
    ){
        try {
            User user = userService.getUser(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success",userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getUsers(){

        try {
            List<User> users = userService.getAllUsers();
            List<UserDto> userDtos = userService.getConvertedUsers(users);
            return ResponseEntity.ok(new ApiResponse("Sucesss",userDtos));
        } catch (RuntimeException e) {
           return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(
            @RequestBody UpdateUserRequest userRequest,
            HttpServletRequest request){
        try {
            User user = userService.getUserByUsername(request);
            User updatetedUser = userService.updateUser(userRequest,user.getId());
            UserDto userDto = userService.convertUserToDto(updatetedUser);
            return ResponseEntity.ok(new ApiResponse("Updated!",userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteUser(HttpServletRequest request){
        try {
            User user = userService.getUserByUsername(request);
            userService.DeleteUser(user.getId());
            return ResponseEntity.ok(new ApiResponse("Deleted SuccessFully",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }


}
