package com.md.blog;

import com.md.blog.dto.*;
import com.md.blog.dto.requests.*;
import com.md.blog.model.Comment;
import com.md.blog.model.Post;
import com.md.blog.model.PostTags;
import com.md.blog.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class TestSupport {

    public User generateUser() {
        return new User(
                "uid",
                "username",
                "email",
                LocalDateTime.of(2021, 11, 11, 11, 11),
                LocalDateTime.of(2021, 11, 11, 11, 11)
        );
    }

    public UserDto generateUserDto() {
        return new UserDto(
                "uid",
                "username",
                "email",
                LocalDateTime.of(2021, 11, 11, 11, 11),
                Collections.emptyList(),
                Collections.emptyList()
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
                "email"
        );
    }

    public User generateUpdatedUser(User from, UpdateUserRequest updateUserRequest) {
        return new User(
                from.getUid(),
                updateUserRequest.getUsername(),
                updateUserRequest.getEmail(),
                from.getCreationDate(),
                from.getUpdatedDate(),
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

    public PostDto generatePostDto() {
        UserDto userDto = generateUserDto();
        return new PostDto(
                "pid",
                "title",
                "body",
                PostTags.CODE,
                LocalDateTime.of(2021, 11, 11, 11, 11),
                LocalDateTime.of(2021, 11, 11, 11, 11),
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
       // User user = generateUser();
        return new CreatePostRequest(
                "title",
                "body",
                PostTags.CODE,
                "uid"
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
                from.getCreationDate(),
                from.getUpdatedDate(),
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
                LocalDateTime.of(2021, 11, 11, 11, 11),
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
