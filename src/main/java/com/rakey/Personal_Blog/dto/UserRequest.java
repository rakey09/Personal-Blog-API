package com.rakey.Personal_Blog.dto;

public record UserRequest(
        String username,
        String email,
        String password,
        String bio
) {
}
