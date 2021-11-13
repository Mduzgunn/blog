package com.md.blog;

import com.md.blog.dto.UserDto;
import com.md.blog.dto.requests.CreateUserRequest;
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
                LocalDateTime.of(2021, 11, 13, 13, 13),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

    public UserDto generateUserDto(){
        return new UserDto(
                "uid",
                "username",
                "email",
               // 2121-11-12-13:13,
                LocalDateTime.of(2021, 11, 13, 13, 13,22),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

    public List<User> generateListofUser(){
        User user = generateUser();
        return List.of(user);
    }

    public List<UserDto> generateListofUserDto(){
        UserDto user = generateUserDto();
        return List.of(user);
    }

    public CreateUserRequest generateCreateUserRequest(){
        return new CreateUserRequest(
                "username",
                "email"
        );
    }


}
