package com.md.blog.service;

import com.md.blog.model.Comment;
import com.md.blog.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    public CommentService(CommentRepository commentRepository){
        this.commentRepository=commentRepository;
    }

    protected Comment getCommentById(String id){
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("comment not found"));
    }
}
