package com.rakey.Personal_Blog.service;

import com.rakey.Personal_Blog.config.jwt.JwtService;
import com.rakey.Personal_Blog.dto.UpdateUserRequest;
import com.rakey.Personal_Blog.dto.UserDto;
import com.rakey.Personal_Blog.dto.UserRequest;
import com.rakey.Personal_Blog.exception.AlreadyExistsException;
import com.rakey.Personal_Blog.exception.ResourceNotFoundException;
import com.rakey.Personal_Blog.model.User;
import com.rakey.Personal_Blog.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper mapper;
    private final JwtService jwtService;
    @Override
    public User CreateUser(UserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.email()))
                .map(req -> {
                    User user = new User();
                    user.setUsername(request.username());
                    user.setEmail(request.email());
                    user.setPassword(encoder.encode(request.password()));
                    user.setBio(request.bio());
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("Oops! "+request.email()+" already Exists"));
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UpdateUserRequest request,Long id) {
        return userRepository.findById(id).map(existingUser ->{
            existingUser.setBio(request.bio());
            existingUser.setUsername(request.username());
            return userRepository.save(existingUser);
        }).orElseThrow(()-> new ResourceNotFoundException("User Not found!"));
    }

    @Override
    public void DeleteUser(Long id) {
     userRepository.findById(id).ifPresentOrElse(userRepository ::delete, ()->
     {
         throw new ResourceNotFoundException("User Not Found");
     });
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return mapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getConvertedUsers(List<User> users) {
        return users.stream().map(this::convertUserToDto).toList();
    }
    @Override
    public User getUserByUsername(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        // getting the JWT token from authHeader
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            // extracting the username from the JWT token
            userName = jwtService.extractUsername(token);
        }
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));;
        return user;
    }
}
