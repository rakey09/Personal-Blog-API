package com.rakey.Personal_Blog.controller;

import com.rakey.Personal_Blog.dto.ApiResponse;
import com.rakey.Personal_Blog.dto.PostDto;
import com.rakey.Personal_Blog.dto.PostRequest;
import com.rakey.Personal_Blog.dto.UpdatePostRequest;
import com.rakey.Personal_Blog.exception.AlreadyExistsException;
import com.rakey.Personal_Blog.exception.ResourceNotFoundException;
import com.rakey.Personal_Blog.exception.UnauthorizedActionException;
import com.rakey.Personal_Blog.model.Post;
import com.rakey.Personal_Blog.model.User;
import com.rakey.Personal_Blog.service.IPostService;
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
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final IPostService postService;
    private final IUserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createPost(
            @RequestBody PostRequest postRequest,
            HttpServletRequest request
    ){
        try {
            User user = userService.getUserByUsername(request);
            Post post = postService.createPost(postRequest,user);
            PostDto postDto = postService.convertPostToDto(post);
            return ResponseEntity.ok(new ApiResponse("Success",postDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/Posts")
    public ResponseEntity<ApiResponse> getAllPosts(){
        try {
            List<Post> post = postService.getPosts();
            List<PostDto> postDtos = postService.getConvertedPosts(post);
            return ResponseEntity.ok(new ApiResponse("Sucess",postDtos));
        } catch (RuntimeException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{postId}/update")
    public ResponseEntity<ApiResponse> updatePost(
            @RequestBody UpdatePostRequest postRequest,
            @PathVariable Long postId,
            HttpServletRequest request
    ){
        try {
            User user = userService.getUserByUsername(request);
            Post post = postService.updatePost(postRequest,postId, user);
            PostDto postDto = postService.convertPostToDto(post);
            return ResponseEntity.ok(new ApiResponse("Successfully Updated",postDto));
        } catch (ResourceNotFoundException | UnauthorizedActionException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> getPostById(
            @PathVariable Long postId
    ){
        try {
            Post post = postService.getPostById(postId);
            PostDto postDto = postService.convertPostToDto(post);
            return ResponseEntity.ok(new ApiResponse("Success",postDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/post/{tag}")
    public ResponseEntity<ApiResponse> getPostByTag(
            @PathVariable String tag
    ){
        try {
            List<Post> post = postService.getPostsByTag(tag);
            List<PostDto> postDtos = postService.getConvertedPosts(post);
            return ResponseEntity.ok(new ApiResponse("Success",postDtos));
        } catch (RuntimeException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{postId}/delete")
    public ResponseEntity<ApiResponse> deletePost(
            @PathVariable Long postId,
            HttpServletRequest request
    ){
        try {
            User user = userService.getUserByUsername(request);
            postService.deletePost(postId,user);
            return ResponseEntity.ok(new ApiResponse("SuccessFully Deleted!",null));
        } catch (ResourceNotFoundException | UnauthorizedActionException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
