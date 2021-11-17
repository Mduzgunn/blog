package com.md.blog.service;

import com.md.blog.TestSupport;
import com.md.blog.dto.CommentDto;
import com.md.blog.dto.converter.CommentDtoConverter;
import com.md.blog.dto.requests.CreateCommentRequest;
import com.md.blog.exception.NotFoundException;
import com.md.blog.model.Comment;
import com.md.blog.model.Post;
import com.md.blog.model.User;
import com.md.blog.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void testGetCommentById_whenCalledWithExistId_itShouldReturnPostDto(){

        Comment comment = generateComment();
        CommentDto commentDto = generateCommentDto();

        Mockito.when(commentRepository.findById("cid")).thenReturn(Optional.of(comment));
        Mockito.when(commentDtoConverter.convertToCommentDto(comment)).thenReturn(commentDto);

        CommentDto result = commentService.getCommentById("cid");

        assertEquals(commentDto,result);

        Mockito.verify(commentRepository).findById("cid");
        Mockito.verify(commentDtoConverter).convertToCommentDto(comment);
    }

    @Test
    void testGetCommentById_whenIdNotExist_itShouldThrowNotFoundException(){

        Mockito.when(commentRepository.findById("cid")).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> commentService.getCommentById("cid"));

        Mockito.verify(commentRepository).findById("cid");
        Mockito.verifyNoInteractions(commentDtoConverter);
    }

    @Test
    void testGetCommentsList_itShouldReturnListOfCommentDto(){

        List<Comment> commentList = generateCommentList();
        List<CommentDto> commentDtoList = generateCommentDtoList();

        Mockito.when(commentRepository.findAll()).thenReturn(commentList);
        Mockito.when(commentDtoConverter.convertToCommentDtoList(commentList)).thenReturn(commentDtoList);

        List<CommentDto> result = commentService.getAllCommentDtos();

        assertEquals(commentDtoList,result);

        Mockito.verify(commentRepository).findAll();
        Mockito.verify(commentDtoConverter).convertToCommentDtoList(commentList);
    }

    @Test
    void testCreateComment_whenCreateCommentRequest_itShouldReturnCommentDto(){

        CreateCommentRequest createCommentRequest = generateCreateCommentRequest();
        Comment comment = generateComment();
        CommentDto commentDto = generateCommentDto();
        User user = generateUser();
        Post post = generatePost();

        Mockito.when(userService.findUserById("uid")).thenReturn(user);
        Mockito.when(postService.findPostById("pid")).thenReturn(post);
        Mockito.when(commentDtoConverter.convertToCommentDto(commentRepository.save(comment))).thenReturn(commentDto);

        CommentDto result = commentService.createComment(createCommentRequest);

        assertEquals(commentDto, result);

        Mockito.verify(userService).findUserById("uid");
        Mockito.verify(postService).findPostById("pid");
        Mockito.verify(commentDtoConverter).convertToCommentDto(commentRepository.save(comment));
    }

     @Test
    void testCreateComment_whenUserIdDoesNotExist_itThrowNotFoundException(){

        CreateCommentRequest createCommentRequest=generateCreateCommentRequest();

        Mockito.when(userService.findUserById("uid")).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> commentService.createComment(createCommentRequest));

        Mockito.verify(userService).findUserById("uid");
        Mockito.verifyNoInteractions(commentDtoConverter);
        Mockito.verifyNoInteractions(commentRepository);
     }
//TODO***********************
    @Test
    void testCreateComment_whenUserIdExistAndPostIdDoesNotExist_itThrowNotFoundException(){

        CreateCommentRequest createCommentRequest=generateCreateCommentRequest();

        Mockito.when(userService.findUserById(createCommentRequest.getUid())).thenReturn(generateUser());
        Mockito.when(postService.findPostById(createCommentRequest.getPid())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> commentService.createComment(createCommentRequest));

//        Mockito.verify(userService).findUserById("uid");
        Mockito.verify(postService).findPostById("pid");
        Mockito.verifyNoInteractions(commentDtoConverter);
        Mockito.verifyNoInteractions(commentRepository);
    }


    @Test
    void testDeleteCommentById_whenValidId_itShouldReturnString(){

        Comment comment = generateComment();
        CommentDto commentDto = generateCommentDto();

        Mockito.when(commentRepository.findById("cid")).thenReturn(Optional.of(comment));
        Mockito.when(commentDtoConverter.convertToCommentDto(comment)).thenReturn(commentDto);

        String result = commentService.deleteCommentById("cid");

        assertEquals("comment deleted successfully", result);

        Mockito.verify(commentRepository).findById("cid");
        Mockito.verify(commentDtoConverter).convertToCommentDto(comment);
    }

    @Test
    void testDeleteCommentById_whenInvalidId_itShouldThrowNotFoundException(){

        Mockito.when(commentRepository.findById("cid")).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> commentService.getCommentById("cid"));

        Mockito.verify(commentRepository).findById("cid");
        Mockito.verifyNoInteractions(commentDtoConverter);
    }
}