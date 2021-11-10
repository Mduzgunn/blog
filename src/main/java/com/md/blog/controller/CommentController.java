package com.md.blog.controller;


import com.md.blog.model.Comment;
import com.md.blog.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/comment")
public class CommentController {
    private final CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService=commentService;
    }
//    @GetMapping
//    public ResponseEntity<List<CommentDto>> getAllComments(){
//        return ResponseEntity.ok(commentService.getAllCommentList());
//    }
@GetMapping
public ResponseEntity<List<Comment>> getAllComments(){
    return ResponseEntity.ok(commentService.getAllCommentList());
}


}
