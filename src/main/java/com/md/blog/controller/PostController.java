package com.md.blog.controller;

import com.md.blog.dto.PostDto;
import com.md.blog.dto.requests.CreatePostRequest;
import com.md.blog.dto.requests.UpdatePostRequest;
import com.md.blog.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<PostDto> createPost(@PathVariable String id, @Valid @RequestBody CreatePostRequest createPostRequest) {
        return ResponseEntity.ok(postService.createPost(id,createPostRequest));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable String id, @RequestBody UpdatePostRequest updatePostRequest) {
        return ResponseEntity.ok(postService.updatePost(id, updatePostRequest));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts() {
        return ResponseEntity.ok(postService.getAllPostDtoList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getUserByID(@PathVariable String id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable String id) {
        return ResponseEntity.ok(postService.deletePostById(id));
    }


}
