package com.md.blog.service;

import com.md.blog.TestSupport;
import com.md.blog.dto.PostDto;
import com.md.blog.dto.converter.PostDtoConverter;
import com.md.blog.dto.requests.CreatePostRequest;
import com.md.blog.dto.requests.UpdatePostRequest;
import com.md.blog.exception.PostNotFoundException;
import com.md.blog.exception.UserNotFoundException;
import com.md.blog.model.Post;
import com.md.blog.model.User;
import com.md.blog.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

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
        postDtoConverter = mock(PostDtoConverter.class);

        postService = new PostService(postRepository,postDtoConverter, userService);
    }


    @Test
    void testGetPostById_whenIdExist_itShouldReturnPostDto() {
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
    void testGetPostById_whenIdNotExists_itShouldThrowNotFoundException() {
        Mockito.when(postRepository.findById("id")).thenThrow(PostNotFoundException.class);

        assertThrows(PostNotFoundException.class, () -> postService.getPostById("id"));

        Mockito.verify(postRepository).findById("id");
        Mockito.verifyNoInteractions(postDtoConverter);
    }

    @Test
    void testGetPostList_itShouldReturnListOfPostDto() {
        List<Post> postList = generatePostList();
        List<PostDto> postDtoList = generatePostDtoList();

        Mockito.when(postRepository.findAll()).thenReturn(postList);
        Mockito.when(postDtoConverter.convertToPostDtoList(postList)).thenReturn(postDtoList);

        List<PostDto> result = postService.getAllPostDtoList();

        assertEquals(postDtoList, result);

        Mockito.verify(postRepository).findAll();
        Mockito.verify(postDtoConverter).convertToPostDtoList(postList);
    }

    @Test
    void testCreatePost_whenCreatePostRequest_itShouldReturnPostDto() {
        CreatePostRequest createPostRequest = generateCreatePostRequest();
        Post post = generatePost();
        PostDto postDto =generatePostDto();
        User user = generateUser();

        Mockito.when(userService.findUserById("uid")).thenReturn(user);
        Mockito.when(postDtoConverter.convertToPostDto(postRepository.save(post))).thenReturn(postDto);

        PostDto result = postService.createPost(createPostRequest);

        assertEquals(postDto, result);

        Mockito.verify(userService).findUserById("uid");
        Mockito.verify(postDtoConverter).convertToPostDto(postRepository.save(post));
    }

    @Test
    void testCreatePost_whenUserIdDoesNotExists_itThrowUserNotFoundException() {

        CreatePostRequest createPostRequest = generateCreatePostRequest();

        Mockito.when(userService.findUserById("uid")).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> postService.createPost(createPostRequest));

        Mockito.verify(userService).findUserById("uid");
        Mockito.verifyNoInteractions(postDtoConverter);
        Mockito.verifyNoInteractions(postRepository);
    }

    @Test
    void testUpdatePost_whenUpdatePostRequest_itShouldReturnPostDto() {

        UpdatePostRequest request = generateUpdatePostRequest();
        Post updatedPost = generateUpdatedPost(generatePost(), request);
        PostDto postDto = generatePostDto();
        User user = generateUser();

        Mockito.when(postRepository.findById("pid")).thenReturn(Optional.of(generatePost()));
        Mockito.when(postDtoConverter.convertToPostDto(postRepository.save(updatedPost))).thenReturn(postDto);

        PostDto result = postService.updatePost("pid", request);

        assertEquals(postDto, result);

        Mockito.verify(postRepository).findById("pid");
        Mockito.verify(postDtoConverter).convertToPostDto(postRepository.save(updatedPost));
    }

    @Test
    void testUpdatePost_whenUpdatePostRequestButInvalidId_itShouldThrowPostNotFoundException() {

        UpdatePostRequest request = generateUpdatePostRequest();

        Mockito.when(postRepository.findById("pid")).thenThrow(PostNotFoundException.class);

        assertThrows(PostNotFoundException.class, () -> postService.updatePost("pid", request));

        Mockito.verify(postRepository).findById("pid");
        Mockito.verifyNoInteractions(postDtoConverter);
    }

    @Test
    void testDeletePostById_whenValidId_itShouldReturnString() {

        Post post = generatePost();
        PostDto postDto = generatePostDto();

        Mockito.when(postRepository.findById("pid")).thenReturn(Optional.of(post));
        Mockito.when(postDtoConverter.convertToPostDto(post)).thenReturn(postDto);

        String result = postService.deletePostById("pid");

        assertEquals("post deleted successfully " + "pid", result);

        Mockito.verify(postRepository).findById("pid");
        Mockito.verify(postDtoConverter).convertToPostDto(post);
    }

    @Test
    void testDeletePost_whenInvalidId_itShouldThrowNotFoundException() {

        Mockito.when(postRepository.findById("pid")).thenThrow(PostNotFoundException.class);

        assertThrows(PostNotFoundException.class, () -> postService.getPostById("pid"));

        Mockito.verify(postRepository).findById("pid");
        Mockito.verifyNoInteractions(postDtoConverter);
    }

}