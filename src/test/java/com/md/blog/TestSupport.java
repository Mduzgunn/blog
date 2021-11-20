package com.md.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.md.blog.dto.*;
import com.md.blog.dto.requests.*;
import com.md.blog.model.Comment;
import com.md.blog.model.Post;
import com.md.blog.model.PostTags;
import com.md.blog.model.User;
import org.junit.jupiter.api.BeforeEach;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class TestSupport {

    public Instant getCurrentInstant() {
        String instantExpected = "2021-06-15T10:15:30Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), Clock.systemDefaultZone().getZone());

        return Instant.now(clock);
    }

    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.ofInstant(getCurrentInstant(), Clock.systemDefaultZone().getZone());
    }
    //private LocalDateTime date=LocalDateTime.of(2021, 11, 11, 11, 11);

    public User generateUser() {
        return new User(
                "uid",
                "username",
                "email"
        );
    }

    public UserDto generateUserDto() {
        return new UserDto(
                "uid",
                "username",
                "email",
                getLocalDateTime()
        );
    }

    public List<User> generateListsUser() {
        User user = generateUser();
        return List.of(user);
    }

    public List<UserDto> generateListsUserDto() {
        UserDto userDto = generateUserDto();
        return List.of(userDto);
    }

    public CreateUserRequest generateCreateUserRequest() {
        return new CreateUserRequest(
                "username",
                "deneme@gmail.com"
        );
    }
    public UpdateUserRequest generateUpdateUserRequest() {
        return new UpdateUserRequest(
                "username",
                "mail"
        );
    }

    public User generateUpdatedUser(User from, UpdateUserRequest updateUserRequest) {
        return new User(
                from.getUid(),
                updateUserRequest.getUsername(),
                updateUserRequest.getEmail(),
                getLocalDateTime(),
                getLocalDateTime(),
                from.getPost(),
                from.getComment()
        );
    }

//        *******post*******

    public Post generatePost() {
        User user = generateUser();
        return new Post(
                "title",
                "body",
                PostTags.CODE,
                user
        );
    }

//    public Post generatePost() {
//        User user = generateUser();
//        return new Post(
//                "pid",
//                "title",
//                "body",
//                "PostTags.CODE",
//                "",
//                ""
//                "user"
//        );
//    }


    public PostDto generatePostDto() {
        UserDto userDto = generateUserDto();
        return new PostDto(
                "pid",
                "title",
                "body",
                PostTags.CODE,
                getLocalDateTime(),
                getLocalDateTime(),
                userDto,
                Collections.emptyList()
        );
    }

    public List<Post> generatePostList() {
        return List.of(generatePost());
    }

    public List<PostDto> generatePostDtoList() {
        return List.of(generatePostDto());
    }

    public CreatePostRequest generateCreatePostRequest() {
        User user = generateUser();
        return new CreatePostRequest(
                "title",
                "body",
                PostTags.CODE,
                user.getUid()
        );
    }

    public UpdatePostRequest generateUpdatePostRequest() {
        return new UpdatePostRequest(
                "title",
                "body",
                PostTags.CUSTOM);
    }

    public Post generateUpdatedPost(Post from, UpdatePostRequest request) {
        return new Post(
                from.getPid(),
                request.getTitle(),
                request.getBody(),
                request.getPostTags(),
                getLocalDateTime(),
                getLocalDateTime(),
                from.getUser(),
                from.getComment()
        );
    }

//            ***Comment***

    public Comment generateComment(){
        Post post = generatePost();
        User user = generateUser();
        return new Comment(
                "comment",
                user,
                post
        );
    }

    public CommentDto generateCommentDto(){
        UserDto userDto = generateUserDto();
        return new CommentDto(
                "cid",
                "comment",
                getLocalDateTime(),
                userDto
        );
    }

    public List<Comment> generateCommentList(){
        return List.of(generateComment());
    }

    public List<CommentDto> generateCommentDtoList(){
        return List.of(generateCommentDto());
    }

    public CreateCommentRequest generateCreateCommentRequest(){
        return new CreateCommentRequest(
                "uid",
                "pid",
                "comment"
        );
    }
}
