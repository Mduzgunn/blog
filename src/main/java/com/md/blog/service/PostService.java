package com.md.blog.service;

import com.md.blog.dto.PostDto;
import com.md.blog.dto.converter.PostDtoConverter;
import com.md.blog.dto.requests.CreatePostRequest;
import com.md.blog.dto.requests.UpdatePostRequest;
import com.md.blog.exception.NotFoundException;
import com.md.blog.model.Post;
import com.md.blog.model.User;
import com.md.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new NotFoundException("post not found"));
    }

    public PostDto getPostById(String id) {
        return postDtoConverter.convertToPostDto(findPostById(id));
    }

    protected List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    public List<PostDto> getAllPostDtos() {
        return postDtoConverter.convertToPostDtoList(getAllPosts());
    }

    public PostDto createPost(String id,CreatePostRequest createPostRequest) {
        User user = userService.findUserById(id);

        Post post = new Post(
                createPostRequest.getTitle(),
                createPostRequest.getBody(),
                createPostRequest.getTags(),
                user
        );
        return postDtoConverter.convertToPostDto(postRepository.save(post));
    }

//    public List<PostDto> getAllPosts() {
//        return postRepository.findAll().stream().
//                map(postDtoConverter::convertToPostDto).collect(Collectors.toList());
//    }

    public String deletePostById(String id) {
        getPostById(id);
        postRepository.deleteById(id);
        return "post deleted successfully";
    }

    public PostDto updatePost(String pid, UpdatePostRequest updatePostRequest) {

        Post post = findPostById(pid);

        Post updatedPost = new Post(
                post.getPid(),
                updatePostRequest.getTitle(),
                updatePostRequest.getBody(),
                updatePostRequest.getPostTags(),
                post.getCreationDate(),
                LocalDateTime.now(),
                post.getUser(),
                post.getComment()
        );

        return postDtoConverter.convertToPostDto(postRepository.save(updatedPost));
    }




}
