package com.rakey.Personal_Blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private Object data;
}
