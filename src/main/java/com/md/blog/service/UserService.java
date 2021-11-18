package com.md.blog.service;

import com.md.blog.dto.PostDto;
import com.md.blog.dto.UserDto;
import com.md.blog.dto.converter.UserDtoConverter;
import com.md.blog.dto.requests.CreateUserRequest;
import com.md.blog.dto.requests.UpdatePostRequest;
import com.md.blog.dto.requests.UpdateUserRequest;
import com.md.blog.exception.UserNotFoundException;
import com.md.blog.model.Post;
import com.md.blog.model.User;
import com.md.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    protected User findUserById(String id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("user not found"+id));
    }

    protected List<User> getAllUserList(){
        return userRepository.findAll();
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

    public List<UserDto> getAllUsers(){
        return userDtoConverter.convertToUserDtoList(getAllUserList());
    }

    public String deleteUserById(String id) {
        getUserById(id);
        userRepository.deleteById(id);
        return "user deleted successfully"+id;
    }

    public UserDto updateUser(String uid, UpdateUserRequest updateUserRequest) {
        User user = findUserById(uid);

        User updatedUser = new User(
                user.getUid(),
                updateUserRequest.getUsername(),
                updateUserRequest.getEmail(),
                user.getCreationDate(),
                LocalDateTime.now()
        );
        return userDtoConverter.convertToUserDto(userRepository.save(updatedUser));
    }



}
