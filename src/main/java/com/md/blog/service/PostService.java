package com.md.blog.service;

import com.md.blog.dto.PostDto;
import com.md.blog.dto.converter.PostDtoConverter;
import com.md.blog.dto.requests.CreatePostRequest;
import com.md.blog.exception.NotFoundException;
import com.md.blog.model.Post;
import com.md.blog.model.User;
import com.md.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final PostDtoConverter postDtoConverter;

    public PostService(PostRepository postRepository,
                       UserService userService,
                       PostDtoConverter postDtoConverter) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.postDtoConverter = postDtoConverter;
    }

    protected Post findPostById(String id) {
        return postRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("post not found"));
    }

    public PostDto getPostById(String id) {
        return postDtoConverter.convertToPostDto(findPostById(id));
    }

    public PostDto createPost(CreatePostRequest createPostRequest) {
        User user = userService.findUserById(createPostRequest.getUid());

        Post post = new Post(
                createPostRequest.getTitle(),
                createPostRequest.getBody(),
                createPostRequest.getTags(),
                user
        );
        return postDtoConverter.convertToPostDto(postRepository.save(post));
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream().
                map(postDtoConverter::convertToPostDto).collect(Collectors.toList());
    }

    public String deletePostById(String id) {
        getPostById(id);
        postRepository.deleteById(id);
        return "post deleted successfully";
    }

}
