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

    @PostMapping("/{pid}")
    public ResponseEntity<PostDto> createPost(@PathVariable String pid, @Valid @RequestBody CreatePostRequest createPostRequest) {
        return ResponseEntity.ok(postService.createPost(pid,createPostRequest));
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<PostDto> updatePost(@PathVariable String pid,
//                                              @Valid @RequestBody UpdatePostRequest request) {
//        return ResponseEntity.ok(postService.updatePost(pid, request));
//    }
    @PutMapping(value = "/{pid}")
    public ResponseEntity<PostDto> updatePost(@PathVariable String pid, @RequestBody UpdatePostRequest updatePostRequest) {
        return ResponseEntity.ok(postService.updatePost(pid, updatePostRequest));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts() {
        return ResponseEntity.ok(postService.getAllPostDtos());
    }

    @GetMapping("/{pid}")
    public ResponseEntity<PostDto> getUserByID(@PathVariable String pid) {
        return ResponseEntity.ok(postService.getPostById(pid));
    }

    @DeleteMapping("/{pid}")
    public ResponseEntity<String> deletePostById(@PathVariable String pid) {
        return ResponseEntity.ok(postService.deletePostById(pid));
    }


}
