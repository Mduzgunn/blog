package com.md.blog.service;

import com.md.blog.TestSupport;
import com.md.blog.dto.PostDto;
import com.md.blog.dto.converter.PostDtoConverter;
import com.md.blog.dto.requests.CreatePostRequest;
import com.md.blog.dto.requests.UpdatePostRequest;
import com.md.blog.exception.NotFoundException;
import com.md.blog.model.Post;
import com.md.blog.model.User;
import com.md.blog.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostServiceTest extends TestSupport {

    private PostRepository postRepository;
    private PostDtoConverter postDtoConverter;
    private UserService userService;

    private PostService postService;

    @BeforeEach
    void setUp() {

        postRepository = Mockito.mock(PostRepository.class);
        postDtoConverter = Mockito.mock(PostDtoConverter.class);
        userService = Mockito.mock(UserService.class);

        postService = new PostService(postRepository,postDtoConverter, userService);
    }


    @Test
    void testGetPostById_whenCalledWithExistId_itShouldReturnPostDto() {
        Post post = generatePost();
        PostDto postDto = generatePostDto();

        Mockito.when(postRepository.findById("id")).thenReturn(Optional.of(post));
        Mockito.when(postDtoConverter.convertToPostDto(post)).thenReturn(postDto);

        PostDto result = postService.getPostById("id");

        assertEquals(postDto, result);

        Mockito.verify(postRepository).findById("id");
        Mockito.verify(postDtoConverter).convertToPostDto(post);

    }

    @Test
    void testGetPostById_whenCalledIdNotExists_itShouldThrowNotFoundException() {

        Mockito.when(postRepository.findById("id")).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> postService.getPostById("id"));

        Mockito.verify(postRepository).findById("id");
        Mockito.verifyNoInteractions(postDtoConverter);
    }

    @Test
    void testGetPostList_itShouldReturnListOfPostDto() {
        List<Post> postList = generatePostList();
        List<PostDto> postDtoList = generatePostDtoList();

        Mockito.when(postRepository.findAll()).thenReturn(postList);
        Mockito.when(postDtoConverter.convertToPostDtoList(postList)).thenReturn(postDtoList);

        List<PostDto> result = postService.getAllPostDtos();

        assertEquals(postDtoList, result);

        Mockito.verify(postRepository).findAll();
        Mockito.verify(postDtoConverter).convertToPostDtoList(postList);
    }

//    @Test
//    void testCreatePost_whenCalledCreatePostRequest_itShouldReturnPostDto() {
//        CreatePostRequest createPostRequest = generateCreatePostRequest();
//        Post post = generatePostWithFields(createPostRequest.getTitle(), createPostRequest.getBody());
//        PostDto postDto = generatePostDto();
//
//        Mockito.when(userService.findUserById("id")).thenReturn(generateUser());
//        Mockito.when(postDtoConverter.convertToPostDto(post)).thenReturn(postDto);
//        Mockito.when(postRepository.save(post)).thenReturn(post);
//
//        PostDto result = postService.createPost("id",createPostRequest);
//
//        assertEquals(postDto, result);
//
//        Mockito.verify(userService).findUserById("id");
//        Mockito.verify(postDtoConverter).convertToPostDto(post);
//        Mockito.verify(postRepository).save(post);
//    }

    @Test
    void testCreatePost_whenCalledCreatePostRequest_itShouldReturnPostDto() {
        CreatePostRequest createPostRequest = generateCreatePostRequest();
        Post post = generatePost();
        PostDto postDto =generatePostDto();
        User user = generateUser();


        Mockito.when(userService.findUserById("id")).thenReturn(user);
        Mockito.when(postDtoConverter.convertToPostDto(postRepository.save(post))).thenReturn(postDto);

        PostDto result = postService.createPost("id",createPostRequest);

        assertEquals(postDto, result);

        Mockito.verify(userService).findUserById("id");
        Mockito.verify(postDtoConverter).convertToPostDto(postRepository.save(post));
    }

    @Test
    void testCreatePost_whenUserDoesntExists_itThrowNotFoundException() {

        CreatePostRequest createPostRequest = generateCreatePostRequest();

        Mockito.when(userService.findUserById("pid")).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> postService.createPost("pid",createPostRequest));

        Mockito.verify(userService).findUserById("pid");
        Mockito.verifyNoInteractions(postDtoConverter);
        Mockito.verifyNoInteractions(postRepository);
    }

    @Test
    void testUpdatePost_whenCalledUpdatePostRequest_itShouldReturnPostDto() {

        UpdatePostRequest request = generateUpdatePostRequest();
        Post updatedPost = generateUpdatedPost(generatePost(), request);
        PostDto postDto = generatePostDto();
        User user = generateUser();

        Mockito.when(postRepository.findById("pid")).thenReturn(Optional.ofNullable(generatePost()));
        Mockito.when(postDtoConverter.convertToPostDto(postRepository.save(updatedPost))).thenReturn(postDto);

        PostDto result = postService.updatePost("pid", request);

        assertEquals(postDto, result);

        Mockito.verify(postRepository).findById("pid");
        Mockito.verify(postDtoConverter).convertToPostDto(postRepository.save(updatedPost));
    }

    @Test
    void testUpdatePost_whenCalledUpdatePostRequestButInvalidId_itShouldThrowNotFoundException() {

        UpdatePostRequest request = generateUpdatePostRequest();

        Mockito.when(postRepository.findById("pid")).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> postService.updatePost("pid", request));

        Mockito.verify(postRepository).findById("pid");
        Mockito.verifyNoInteractions(postDtoConverter);
    }

    @Test
    void testDeletePost_whenCalledValidId_itShouldReturnString() {

        Post post = generatePost();
        PostDto postDto = generatePostDto();

        Mockito.when(postRepository.findById("pid")).thenReturn(Optional.ofNullable(post));
        Mockito.when(postDtoConverter.convertToPostDto(post)).thenReturn(postDto);

        String result = postService.deletePostById("pid");

        assertEquals("post deleted successfully", result);

        Mockito.verify(postRepository).findById("pid");
        Mockito.verify(postDtoConverter).convertToPostDto(post);
    }

    @Test
    void testDeletePost_whenCalledInvalidId_itShouldThrowNotFoundException() {

        Mockito.when(postRepository.findById("pid")).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> postService.getPostById("pid"));

        Mockito.verify(postRepository).findById("pid");
        Mockito.verifyNoInteractions(postDtoConverter);
    }

}