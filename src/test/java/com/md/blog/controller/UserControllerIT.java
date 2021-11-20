package com.md.blog.controller;

import com.md.blog.IntegrationTestSupport;
import com.md.blog.dto.requests.CreateUserRequest;
import com.md.blog.dto.requests.UpdateUserRequest;
import com.md.blog.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerIT extends IntegrationTestSupport {

    //her test sonunda dbdeki veriler silinir boylece beklenen verı değişmez
    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testGetUserById_whenUserIdExist_shouldReturnUserDto() throws Exception {

        User user = userRepository.save(generateUser());

        this.mockMvc.perform(get("/v1/user/" + user.getUid())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.uid", is(user.getUid())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));

        User user1 = userRepository.findById(user.getUid()).get();
        assertEquals(user, user1);

        List<User> userList = userRepository.findAll();
        assertEquals(1, userList.size());
    }

    @Test
    public void testGetUserById_whenUserIdNotExist_shouldReturnUserNotFoundException() throws Exception {

        this.mockMvc.perform(get("/v1/user" + "notExistId")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetAllUsers_shouldReturnEmptyList() throws Exception {

        this.mockMvc.perform(get("/v1/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        List<User> userList = userRepository.findAll();
        assertEquals(0, userList.size());
    }

    @Test
    public void testGetAllUsers_shouldReturnUserDtoList() throws Exception {

        userRepository.save(generateUser());

        this.mockMvc.perform(get("/v1/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        List<User> userList = userRepository.findAll();
        assertEquals(1, userList.size());
    }

    @Test
    public void testCreateUser_whenCreateUserValidRequest_shouldCreateUserAndReturnUserDto() throws Exception {

        CreateUserRequest request = generateCreateUserRequest();

        this.mockMvc.perform(post("/v1/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.username", is("username")))
                .andExpect(jsonPath("$.email", is("deneme@gmail.com")));

        List<User> userList = userRepository.findAll();
        assertEquals(1, userList.size());
    }

    @Test
    public void testCreateUser_whenInvalidIdCreateUserRequest_shouldReturn4xxBadRequest() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest(
                "",
                "InvalidMail"
        );

        this.mockMvc.perform(post("/v1/user/").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username", is("must not be blank")))
                .andExpect(jsonPath("$.email", is("must be a well-formed email address")));

        List<User> userList = userRepository.findAll();
        assertEquals(0, userList.size());
    }

    @Test
    @Transactional
    public void testUpdateUser_whenUpdateUserRequestExistId_shouldUpdateUserAndReturnUserDto() throws Exception {

        User user = userRepository.save(generateUser());
        UpdateUserRequest updateUserRequest = generateUpdateUserRequest();
        User currentUser = generateUpdatedUser(user, updateUserRequest);

        this.mockMvc.perform(put("/v1/user/" + user.getUid())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(updateUserRequest)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.uid", is(user.getUid())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));

        User user1 = userRepository.findById(user.getUid()).get();
        assertEquals(currentUser, user1);

    }

    @Test
    public void testUpdateUser_whenIdNotExistUpdateUserRequest_shouldReturnUserNotFoundException() throws Exception {

        this.mockMvc.perform(put("/v1/user/" + "NotExistId")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testDeleteUserById_whenExistId_shouldDeleteUserReturnString() throws Exception {

        User user = userRepository.save(generateUser());

        this.mockMvc.perform(delete("/v1/user/" + user.getUid())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().string("Content-Type", "text/plain;charset=UTF-8"))
                .andExpect(content().string("user deleted successfully" + user.getUid()));
    }

    @Test
    public void testDeleteUserById_NotExistId_shouldReturnNotFoundException() throws Exception {
        this.mockMvc.perform(delete("/v1/user/" + "NotExistId")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());
    }
}
