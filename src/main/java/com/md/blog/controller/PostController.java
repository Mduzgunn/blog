package com.md.blog.controller;

import com.md.blog.dto.PostDto;
import com.md.blog.dto.requests.CreatePostRequest;
import com.md.blog.dto.requests.UpdatePostRequest;
import com.md.blog.service.PostService;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("v1/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return ResponseEntity.ok(postService.createPost(createPostRequest));
    }

    @PutMapping(value = "/{pid}")
    public ResponseEntity<PostDto> updatePost(@PathVariable String pid,@Valid @RequestBody UpdatePostRequest updatePostRequest) {
        return ResponseEntity.ok(postService.updatePost(pid, updatePostRequest));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts() {
        List<PostDto> postDtoList = postService.getAllPostDtoList();
        postDtoList.forEach(PostController::linkGenerator);
        return ResponseEntity.ok(postDtoList);
    }

    @GetMapping("/{pid}")
    public ResponseEntity<PostDto> getPostByID(@PathVariable String pid) {
        PostDto postDto = postService.getPostById(pid);
        linkGenerator(postDto);
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping("/{pid}")
    public ResponseEntity<String> deletePostById(@PathVariable String pid) {
        return ResponseEntity.ok(postService.deletePostById(pid));
    }

    private static void linkGenerator(PostDto postDto) {
        Link postDtoLink = linkTo(methodOn(PostController.class).getPostByID(postDto.getPid())).withSelfRel();
        postDto.add(postDtoLink);

        postDto.getAuthor().add(linkTo(methodOn(UserController.class).getUserByID(postDto.getAuthor().getUid())).withSelfRel());

        postDto.getComments().forEach(comment -> {
            Link commentLink = linkTo(methodOn(CommentController.class).getCommentByID(comment.getCid())).withSelfRel();
            comment.add(commentLink);
        });
    }

}
