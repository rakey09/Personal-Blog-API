package com.rakey.Personal_Blog.service;

import com.rakey.Personal_Blog.dto.PostDto;
import com.rakey.Personal_Blog.dto.PostRequest;
import com.rakey.Personal_Blog.dto.UpdatePostRequest;
import com.rakey.Personal_Blog.exception.AlreadyExistsException;
import com.rakey.Personal_Blog.exception.ResourceNotFoundException;
import com.rakey.Personal_Blog.exception.UnauthorizedActionException;
import com.rakey.Personal_Blog.model.Post;
import com.rakey.Personal_Blog.model.User;
import com.rakey.Personal_Blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService{
    private final PostRepository postRepository;
    private final ModelMapper mapper;
    @Override
    public Post createPost(PostRequest postRequest, User user) {
        return Optional.of(postRequest)
                .filter(post -> !postRepository.existsByTitle(post.title()))
                .map(req ->{
                    Post post = new Post();
                    post.setTitle(postRequest.title());
                    post.setContent(postRequest.content());
                    post.setUser(user);
                    post.setTag(postRequest.tag());
                    return postRepository.save(post);
                }).orElseThrow(()-> new AlreadyExistsException("Oops!"+postRequest.title()+" already Exists"));

    }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post updatePost(UpdatePostRequest postRequest, Long id, User currentUser) {
        return postRepository.findById(id).map(existingPost -> {

            if (!existingPost.getUser().getId().equals(currentUser.getId())) {
                throw new UnauthorizedActionException("You are not authorized to update this post");
            }

            existingPost.setContent(postRequest.content());
            existingPost.setTag(postRequest.tag());


            return postRepository.save(existingPost);
        }).orElseThrow(() -> new ResourceNotFoundException("Post not found!"));
    }


    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post Not found!"));
    }

    @Override
    public List<Post> getPostsByTag(String tag) {
        return postRepository.findAllByTag(tag);
    }

    @Override
    public void deletePost(Long id, User currentUser) {
        postRepository.findById(id).ifPresentOrElse(post -> {

            if (!post.getUser().getId().equals(currentUser.getId())) {
                throw new UnauthorizedActionException("You are not authorized to delete this post");
            }

            postRepository.delete(post);
        }, () -> {
            throw new ResourceNotFoundException("Post not found");
        });
    }


    @Override
    public PostDto convertPostToDto(Post post) {
        return mapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getConvertedPosts(List<Post> posts) {
        return posts.stream().map(this::convertPostToDto).toList();
    }
}
