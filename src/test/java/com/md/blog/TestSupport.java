package com.md.blog;

import com.md.blog.dto.*;
import com.md.blog.dto.requests.CreateUserRequest;
import com.md.blog.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class TestSupport {




    public User generateUser() {
        return new User(
                "id",
                "username",
                "email"
        );
    }

    public UserDto generateUserDto(){
        return new UserDto(
                "id",
                "username",
                "email"
               // 2121-11-12-13:13,
                //LocalDateTime.of(2021, 11, 13, 13, 13),

        );
    }

    public List<User> generateListsUser(){
        User user = generateUser();
        return List.of(user);
    }

    public List<UserDto> generateListsUserDto(){
        UserDto userDto = generateUserDto();
        return List.of(userDto);
    }

    public CreateUserRequest generateCreateUserRequest(){
        return new CreateUserRequest(
                "username",
                "email"
        );
    }


}
