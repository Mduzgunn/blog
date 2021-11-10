package com.md.blog.service;

import com.md.blog.dto.PostDto;
import com.md.blog.dto.converter.PostDtoConverter;
import com.md.blog.dto.requests.CreatePostRequest;
import com.md.blog.model.Post;
import com.md.blog.model.User;
import com.md.blog.repository.PostRepository;

public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final CommentService commentService;
    private final PostDtoConverter postDtoConverter;

    public PostService(PostRepository postRepository,
                       UserService userService,
                       CommentService commentService,
                       PostDtoConverter postDtoConverter){
        this.postRepository = postRepository;
        this.userService = userService;
        this.commentService = commentService;
        this.postDtoConverter = postDtoConverter;
    }

    public PostDto createPost(CreatePostRequest createPostRequest){
        User user = userService.getUserById(createPostRequest.getAuthor());

        Post post = new Post(
              createPostRequest.getTitle(),
                createPostRequest.getBody(),
                createPostRequest.getTags(),
                user
                );
        return postDtoConverter.convertToPostDto(postRepository.save(post));
    }
}
