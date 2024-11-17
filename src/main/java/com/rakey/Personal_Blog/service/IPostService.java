package com.rakey.Personal_Blog.service;

import com.rakey.Personal_Blog.dto.PostDto;
import com.rakey.Personal_Blog.dto.PostRequest;
import com.rakey.Personal_Blog.dto.UpdatePostRequest;
import com.rakey.Personal_Blog.model.Post;
import com.rakey.Personal_Blog.model.User;

import java.util.List;

public interface IPostService {

    Post createPost(PostRequest post, User user);
    List<Post> getPosts();


    Post updatePost(UpdatePostRequest postRequest, Long id, User currentUser);

    Post getPostById(Long id);
    List<Post> getPostsByTag(String tag);
    void deletePost(Long id, User currentUser);

    PostDto convertPostToDto(Post post);

    List<PostDto> getConvertedPosts(List<Post> posts);
}
