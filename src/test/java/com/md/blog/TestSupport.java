package com.md.blog;

import com.md.blog.dto.*;
import com.md.blog.dto.requests.CreatePostRequest;
import com.md.blog.dto.requests.CreateUserRequest;
import com.md.blog.dto.requests.UpdatePostRequest;
import com.md.blog.model.Post;
import com.md.blog.model.PostTags;
import com.md.blog.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class TestSupport {


    public User generateUser() {
        return new User(
                "id",
                "username",
                "email",
                LocalDateTime.now(),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

    public UserDto generateUserDto() {
        return new UserDto(
                "id",
                "username",
                "email",
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
                "email"
        );
    }

//        *******post*******

    public Post generatePost() {
        User user = generateUser();
        return new Post(
                "",
                "title",
                PostTags.CODE,
                user
        );
    }

    public PostDto generatePostDto() {
        UserDto userDto = generateUserDto();
        return new PostDto(
                "id",
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

}
