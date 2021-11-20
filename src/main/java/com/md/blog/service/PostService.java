package com.md.blog.service;

import com.md.blog.dto.PostDto;
import com.md.blog.dto.converter.PostDtoConverter;
import com.md.blog.dto.requests.CreatePostRequest;
import com.md.blog.dto.requests.UpdatePostRequest;
import com.md.blog.exception.PostNotFoundException;
import com.md.blog.model.Post;
import com.md.blog.model.User;
import com.md.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostDtoConverter postDtoConverter;
    private final UserService userService;



    public PostService(PostRepository postRepository,
                       PostDtoConverter postDtoConverter,
                       UserService userService) {
        this.postRepository = postRepository;
        this.postDtoConverter = postDtoConverter;
        this.userService = userService;
    }

    protected Post findPostById(String id) {
        return postRepository
                .findById(id)
                .orElseThrow(() -> new PostNotFoundException("post not found " + id));
    }

    protected List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public PostDto getPostById(String id) {
        return postDtoConverter.convertToPostDto(findPostById(id));
    }

    public List<PostDto> getAllPostDtoList() {
        return postDtoConverter.convertToPostDtoList(getAllPosts());
    }

    public PostDto createPost(CreatePostRequest createPostRequest) {
        User user =userService.findUserById(createPostRequest.getUid());
        Post post = new Post(
                createPostRequest.getTitle(),
                createPostRequest.getBody(),
                createPostRequest.getTags(),
                user
        );
        return postDtoConverter.convertToPostDto(postRepository.save(post));
    }

    public String deletePostById(String pid) {
        getPostById(pid);
        postRepository.deleteById(pid);
        return "post deleted successfully "+pid;
    }
    public PostDto updatePost(String pid, UpdatePostRequest updatePostRequest) {
        Post post = findPostById(pid);

        Post updatedPost = new Post(
                post.getPid(),
                updatePostRequest.getTitle(),
                updatePostRequest.getBody(),
                updatePostRequest.getPostTags(),
                post.getCreationDate(),
                post.getUpdatedDate(),
                post.getUser(),
                post.getComment()
        );
        return postDtoConverter.convertToPostDto(postRepository.save(updatedPost));
    }
}
