package com.md.blog.controller;

import com.md.blog.IntegrationTestSupport;
import com.md.blog.dto.requests.CreateCommentRequest;
import com.md.blog.model.Comment;
import com.md.blog.model.Post;
import com.md.blog.model.PostTags;
import com.md.blog.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CommentControllerIT extends IntegrationTestSupport {

    private String url = "/v1/comment/";

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
    }

    @Test
    public void testGetAllComments_shouldReturnEmptyList() throws Exception {

        this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        List<Comment> commentsFromDB = commentRepository.findAll();
        assertEquals(0, commentsFromDB.size());
    }

    @Test
    public void testGetAllComments_shouldReturnCommentDtoList() throws Exception {

        User user = userRepository.save(new User("username", "mail"));
        Post post = postRepository.save(new Post("title", "body", PostTags.HISTORY, user));
        commentRepository.save(new Comment("comment", user, post));

        this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        List<Comment> commentsInDb = commentRepository.findAll();
        assertEquals(1, commentsInDb.size());
    }

    @Test
    @Transactional
    public void testGetCommentId_whenValidId_shouldReturnCommentDto() throws Exception {

        User user = userRepository.save(new User("username", "mail"));
        Post post = postRepository.save(new Post("title", "body", PostTags.HISTORY, user));
        Comment comment = commentRepository.save(new Comment("comment", user, post));

        this.mockMvc.perform(get(url + comment.getCid())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.cid", is(comment.getCid())))
                .andExpect(jsonPath("$.comment", is(comment.getComment())))
                .andExpect(jsonPath("$.creationDate", notNullValue()));

        Comment commentInDb = commentRepository.findById(comment.getCid()).get();
        assertEquals(comment, commentInDb);
    }

    @Test
    public void testGetCommentById_whenInvalidId_shouldReturnCommentNotFoundException() throws Exception {

        this.mockMvc.perform(get(url + "not-valid-id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testCreateComment_whenCreateCommentRequestIsInvalid_shouldNotCreateCommentReturn400BadRequest() throws Exception {

        CreateCommentRequest request = new CreateCommentRequest("", "", "");

        this.mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        List<Comment> commentsFromDB = commentRepository.findAll();
        assertEquals(0, commentsFromDB.size());
    }

    @Test
    public void testCreateComment_whenCreateCommentRequestIsValid_shouldCreateCommentReturnCommentDto() throws Exception {
        User user = userRepository.save(new User("username", "mail"));
        Post post = postRepository.save(new Post("title", "body", PostTags.HISTORY, user));
        CreateCommentRequest createCommentRequest = new CreateCommentRequest(
                Objects.requireNonNull(user.getUid()),
                Objects.requireNonNull(post.getPid()),
                "comment");

        this.mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(createCommentRequest)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(new MediaType("application", "json")))
                .andExpect(jsonPath("$.cid", notNullValue()))
                .andExpect(jsonPath("$.comment", is(createCommentRequest.getComment())))
                .andExpect(jsonPath("$.creationDate", notNullValue()));

        List<Comment> commentsFromDB = commentRepository.findAll();
        assertEquals(1, commentsFromDB.size());
    }

    @Test
    public void testDeleteCommentById_whenValidId_shouldDeleteCommentAndReturnString() throws Exception {


        User user = userRepository.save(new User("username", "mail"));
        Post post = postRepository.save(new Post("title", "body", PostTags.HISTORY, user));
        Comment comment = commentRepository.save(new Comment("comment", user, post));

        this.mockMvc.perform(delete(url + comment.getCid())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().string("Content-Type", "text/plain;charset=UTF-8"))
                .andExpect(content().string("comment deleted successfully " + comment.getCid()));
    }

    @Test
    public void testDeleteCommentById_whenCalledInvalidId_shouldReturnNotFoundException() throws Exception {

        this.mockMvc.perform(delete(url + "not-valid-id")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());
    }


}
