package com.md.blog.Service;

import com.md.blog.Model.Comment;
import com.md.blog.Repo.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    public CommentService(CommentRepository commentRepository){
        this.commentRepository=commentRepository;
    }

    public List<Comment> getAllCommentList(){
        return commentRepository.findAll();
    }
}
