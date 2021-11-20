package com.md.blog.controller;

import com.md.blog.IntegrationTestSupport;
import com.md.blog.dto.requests.CreatePostRequest;
import com.md.blog.dto.requests.UpdatePostRequest;
import com.md.blog.dto.requests.UpdateUserRequest;
import com.md.blog.model.Post;
import com.md.blog.model.PostTags;
import com.md.blog.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PostControllerIT extends IntegrationTestSupport {

    private String url = "/v1/post/";

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @BeforeEach
    public void setup() {
        Clock clock = mock(Clock.class);

        when(clock.instant()).thenReturn(getCurrentInstant());
        when(clock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
    }

    @Test
    public void testGetAllPosts_shouldReturnEmptyList() throws Exception {

        this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        List<Post> postFromDb = postRepository.findAll();
        assertEquals(0, postFromDb.size());
    }

    @Test
    public void testGetAllPosts_shouldReturnPostDtoList() throws Exception {

        User user = userRepository.save(new User("username", "mail"));
        postRepository.save(new Post("title", "body", PostTags.HISTORY, user));

        this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        List<Post> postFromDb = postRepository.findAll();
        assertEquals(1, postFromDb.size());
    }

    @Test
    @Transactional
    public void testGetPostById_whenValidId_shouldReturnPostDto() throws Exception {

        User user = userRepository.save(new User("username", "mail"));
        Post post = postRepository.save(new Post("title", "body", PostTags.HISTORY, user));

        this.mockMvc.perform(get(url + post.getPid())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pid", is(post.getPid())));

        Post postFromDb = postRepository.findById(post.getPid()).get();
        assertEquals(post, postFromDb);
    }

    @Test
    public void testGetPostById_whenInvalidId_shouldReturnNotFoundException() throws Exception {

        this.mockMvc.perform(get(url + "not-valid-id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testCreatePost_whenCreatePostRequestIsInvalid_shouldNotCreatePostAndReturn400BadRequest() throws Exception {

        User user = userRepository.save(generateUser());
        CreatePostRequest createPostRequest = new CreatePostRequest(
                "",
                "",
                PostTags.CODE,
                user.getUid()
        );
        this.mockMvc.perform(post("/v1/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(createPostRequest)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", notNullValue()))
                .andExpect(jsonPath("$.body", notNullValue()));

        List<Post> postFromDb = postRepository.findAll();
        assertEquals(0, postFromDb.size());
    }

    @Test
    public void testCreatePost_whenCreatePostRequestIsValid_shouldCreatePostAndReturnPostDto() throws Exception {

        User user = userRepository.save(generateUser());
        CreatePostRequest createPostRequest = new CreatePostRequest(
                "title",
                "body",
                PostTags.CODE,
                user.getUid()
        );
        this.mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(createPostRequest)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pid", notNullValue()))
                .andExpect(jsonPath("$.title", notNullValue()))
                .andExpect(jsonPath("$.body", notNullValue()))
                .andExpect(jsonPath("$.tags", notNullValue()))
                .andExpect(jsonPath("$.author", notNullValue()));

        List<Post> postFromDb = postRepository.findAll();
        assertEquals(1, postFromDb.size());
    }

    @Test
    public void testDeletePostById_whenValidId_shouldDeletePostAndReturnString() throws Exception {

        User user = userRepository.save(new User("username", "mail"));
        Post post = postRepository.save(new Post("title", "body", PostTags.HISTORY, user));

        this.mockMvc.perform(delete(url + post.getPid())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().string("Content-Type", "text/plain;charset=UTF-8"))
                .andExpect(content().string("post deleted successfully " + post.getPid()));
    }

    @Test
    public void testDeletePostById_whenInvalidId_shouldReturnNotFoundException() throws Exception {

        this.mockMvc.perform(delete(url + "not-valid-id")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    public void testUpdatePost_whenExistIdUpdatePostRequest_shouldUpdatePostReturnPostDto() throws Exception {

        User user = userRepository.save(new User("uid", "username", "mail", getLocalDateTime(), getLocalDateTime()));
        Post post = postRepository.save(new Post("pid", "title", "body", PostTags.CODE, getLocalDateTime(), getLocalDateTime(), user, Collections.emptyList()));
        UpdatePostRequest updatePostRequest = generateUpdatePostRequest();
        Post updatedPost = generateUpdatedPost(post, updatePostRequest);

        this.mockMvc.perform(put(url + post.getPid())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(updatePostRequest)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.title", is(updatePostRequest.getTitle())))
                .andExpect(jsonPath("$.body", is(updatePostRequest.getBody())))
                .andExpect(jsonPath("$.tags", is(updatePostRequest.getPostTags().name())));

        Post post1 = postRepository.findById(post.getPid()).get();
        assertEquals(updatedPost, post1);
    }

    @Test
    public void testUpdatePost_whenInvalidIdAndUpdatePostRequest_shouldReturnNotFoundException() throws Exception {

        this.mockMvc.perform(put(url + "not-valid-id")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testUpdatePost_whenValidIdButInvalidUpdatePostRequest_shouldReturnNotFoundException() throws Exception {

        User user = userRepository.save(generateUser());
        Post post = postRepository.save(new Post("title", "body", PostTags.HISTORY, user));
        UpdatePostRequest request = new UpdatePostRequest("", "", PostTags.HISTORY);

        this.mockMvc.perform(put(url + post.getPid())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }


}
