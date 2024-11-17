package com.rakey.Personal_Blog.dto;

import com.rakey.Personal_Blog.model.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class UserDto {
    Long id;
    String username;
    String email;
    String password;
    String bio;
    LocalDate createdAt;
    List<Post> posts;
}
