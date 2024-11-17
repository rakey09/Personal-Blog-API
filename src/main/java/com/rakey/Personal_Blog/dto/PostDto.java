package com.rakey.Personal_Blog.dto;

import com.rakey.Personal_Blog.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private String tag;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
