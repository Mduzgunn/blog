package com.md.blog.service;

import com.md.blog.TestSupport;
import com.md.blog.dto.converter.CommentDtoConverter;
import com.md.blog.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

class CommentServiceTest extends TestSupport {
    private CommentRepository commentRepository;
    private CommentDtoConverter commentDtoConverter;
    private UserService userService;
    private PostService postService;
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        commentRepository = Mockito.mock(CommentRepository.class);
        commentDtoConverter = Mockito.mock(CommentDtoConverter.class);
        userService = Mockito.mock(UserService.class);
        postService = Mockito.mock(PostService.class);

        commentService = new CommentService(commentRepository, commentDtoConverter, postService, userService);
    }






}
