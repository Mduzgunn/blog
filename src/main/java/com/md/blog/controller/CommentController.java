package com.md.blog.controller;

import com.md.blog.dto.CommentDto;
import com.md.blog.dto.PostDto;
import com.md.blog.dto.requests.CreateCommentRequest;
import com.md.blog.service.CommentService;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CreateCommentRequest createCommentRequest) {
        return ResponseEntity.ok(commentService.createComment(createCommentRequest));
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments() {
        List<CommentDto> commentDtoList = commentService.getAllCommentDtoList();
        commentDtoList.forEach(CommentController::getUserLink);
        return ResponseEntity.ok(commentDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentByID(@PathVariable String id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable String id) {
        return ResponseEntity.ok(commentService.deleteCommentById(id));
    }

    private static void getUserLink(CommentDto commentDto){
        commentDto.getAuthor().add(linkTo(methodOn(UserController.class)
                .getUserByID(commentDto.getAuthor().getUid())).withSelfRel());
        commentDto.add(linkTo(methodOn(CommentController.class)
                .getCommentByID(commentDto.getCid())).withSelfRel());
    }
}
