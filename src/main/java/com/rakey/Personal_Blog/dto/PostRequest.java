package com.rakey.Personal_Blog.dto;

import com.rakey.Personal_Blog.model.User;


public record PostRequest(
        String title,
        String content,
        User user,
        String tag
) {
}
