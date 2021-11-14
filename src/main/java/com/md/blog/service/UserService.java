package com.md.blog.service;

import com.md.blog.dto.PostDto;
import com.md.blog.dto.UserDto;
import com.md.blog.dto.converter.UserDtoConverter;
import com.md.blog.dto.requests.CreatePostRequest;
import com.md.blog.dto.requests.CreateUserRequest;
import com.md.blog.exception.NotFoundException;
import com.md.blog.model.Post;
import com.md.blog.model.User;
import com.md.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;


    public UserService(UserRepository userRepository,
                       UserDtoConverter userDtoConverter
                       ){
        this.userRepository=userRepository;
        this.userDtoConverter=userDtoConverter;

    }

    public UserDto createUser(CreateUserRequest createUserRequest){

        User user = new User(
                createUserRequest.getUsername(),
                createUserRequest.getEmail()
        );
        return userDtoConverter.convertToUserDto(userRepository.save(user));
    }


    public UserDto getUserById(String id){
       return userDtoConverter.convertToUserDto(findUserById(id));
    }

    protected User findUserById(String id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("user not found"));
    }


//    public List<UserDto> getAllUsers(){
//        return userRepository.findAll().stream().
//                map(userDtoConverter::convertToUserDto).collect(Collectors.toList());
//    }

    protected List<User> getAllUserList(){
        return userRepository.findAll();
    }

    public List<UserDto> getAllUsers(){
        return userDtoConverter.convertToUserDtoList(getAllUserList());
    }


    public String deleteUserById(String id) {
        getUserById(id);
        userRepository.deleteById(id);
        return "user deleted successfully";
    }




}
