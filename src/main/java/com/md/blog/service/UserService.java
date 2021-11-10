package com.md.blog.service;

import com.md.blog.dto.UserDto;
import com.md.blog.dto.converter.UserDtoConverter;
import com.md.blog.dto.requests.CreateUserRequest;
import com.md.blog.exception.NotFoundException;
import com.md.blog.model.Post;
import com.md.blog.model.User;
import com.md.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;

    public UserService(UserRepository userRepository,
                       UserDtoConverter userDtoConverter){
        this.userRepository=userRepository;
        this.userDtoConverter=userDtoConverter;
    }

   public User getUserById(String id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
   }

   public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream().
                map(userDtoConverter::convertToUserDto).collect(Collectors.toList());
   }

    protected User findByUserId(String id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }





}
