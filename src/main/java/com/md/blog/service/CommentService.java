package com.md.blog.service;

import com.md.blog.dto.CommentDto;
import com.md.blog.dto.converter.CommentDtoConverter;
import com.md.blog.dto.requests.CreateCommentRequest;
import com.md.blog.exception.NotFoundException;
import com.md.blog.model.Comment;
import com.md.blog.model.Post;
import com.md.blog.model.User;
import com.md.blog.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentDtoConverter commentDtoConverter;
    private final UserService userService;
    private final PostService postService;
    public CommentService(CommentRepository commentRepository,
                          CommentDtoConverter commentDtoConverter,
                          PostService postService,
                          UserService userService
                          ){
        this.commentRepository=commentRepository;
        this.commentDtoConverter=commentDtoConverter;
        this.userService=userService;
        this.postService=postService;
    }

    public CommentDto createComment(CreateCommentRequest createCommentRequest){
        Post post = postService.findPostById(createCommentRequest.getPid());
        User user = userService.findUserById(createCommentRequest.getUid());

        Comment comment = new Comment(createCommentRequest.getComment(),
                user,
                post);

        return commentDtoConverter.convertToCommentDto(commentRepository.save(comment));
    }

    protected Comment findCommentById(String id){
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("comment not found"));
    }

    public CommentDto getCommentById(String id){
        return commentDtoConverter.convertToCommentDto(findCommentById(id));
    }

    public List<CommentDto> getAllComments(){
        return commentRepository.findAll().stream().map(commentDtoConverter::convertToCommentDto).collect(Collectors.toList());
    }

    public String deleteCommentById(String id){
        getCommentById(id);
        commentRepository.deleteById(id);
        return "comment deleted successfully";
    }



}
