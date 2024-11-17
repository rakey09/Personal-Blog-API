package com.rakey.Personal_Blog.dto;

import com.rakey.Personal_Blog.model.Post;

import java.sql.Blob;
import java.util.List;

public record UserResponse(
        String username,
        String email,
        String bio,
        List<Post> posts
){
}
