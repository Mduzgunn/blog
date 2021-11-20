package com.md.blog.service;

import com.md.blog.TestSupport;
import com.md.blog.dto.PostDto;
import com.md.blog.dto.UserDto;
import com.md.blog.dto.converter.UserDtoConverter;
import com.md.blog.dto.requests.CreateUserRequest;
import com.md.blog.dto.requests.UpdatePostRequest;
import com.md.blog.dto.requests.UpdateUserRequest;
import com.md.blog.exception.PostNotFoundException;
import com.md.blog.exception.UserNotFoundException;
import com.md.blog.model.Post;
import com.md.blog.model.User;
import com.md.blog.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest extends TestSupport {
    private UserRepository userRepository;
    private UserDtoConverter userDtoConverter;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userDtoConverter = Mockito.mock(UserDtoConverter.class);
        userService = new UserService(userRepository, userDtoConverter);
    }

        @Test
    void testGetAllUsers_itShouldReturnDtoList() {

        List<User> userList = generateListsUser();
        List<UserDto> dtoList = generateListsUserDto();

        Mockito.when(userRepository.findAll()).thenReturn(userList);
        Mockito.when(userDtoConverter.convertToUserDtoList(userList)).thenReturn(dtoList);

        List<UserDto> result = userService.getAllUsers();

        assertEquals(dtoList, result);

        Mockito.verify(userRepository).findAll();
        Mockito.verify(userDtoConverter).convertToUserDtoList(userList);
    }

    @Test
    void testGetUserById_whenGetWithId_itShouldReturnUserDto() {
        User user = generateUser();
        UserDto userDto = generateUserDto();

        Mockito.when(userRepository.findById("id")).thenReturn(Optional.of(user));
        Mockito.when(userDtoConverter.convertToUserDto(user)).thenReturn(userDto);

        UserDto result = userService.getUserById("id");

        assertEquals(userDto, result);

        Mockito.verify(userRepository).findById("id");
        Mockito.verify(userDtoConverter).convertToUserDto(user);
    }

    @Test
    void testGetUserById_whenIdNotExist_itShouldThrowUserNotFoundException() {

        Mockito.when(userRepository.findById("id")).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userService.getUserById("id"));

        Mockito.verify(userRepository).findById("id");
        Mockito.verifyNoInteractions(userDtoConverter);
    }

    @Test
    void testCreateUser_whenGetValidRequest_itShouldReturnUserDto() {
        CreateUserRequest createUserRequest = generateCreateUserRequest();
        User expectedUser = generateUser();
        UserDto expectedUserDto = generateUserDto();

        Mockito.when(userRepository.save(expectedUser)).thenReturn(expectedUser);
        Mockito.when(userDtoConverter.convertToUserDto(expectedUser)).thenReturn(expectedUserDto);


        UserDto result = userService.createUser(createUserRequest);

        Assertions.assertEquals(expectedUserDto, result);

        Mockito.verify(userDtoConverter).convertToUserDto(userRepository.save(expectedUser));
    }

    @Test
    void testUpdatePost_whenGetUpdatePostRequest_itShouldReturnPostDto() {

        UpdateUserRequest updateUserRequest = generateUpdateUserRequest();
        User updatedUser = generateUpdatedUser(generateUser(), updateUserRequest);
        UserDto userDto = generateUserDto();

        Mockito.when(userRepository.findById("uid")).thenReturn(Optional.of(generateUser()));
        Mockito.when(userDtoConverter.convertToUserDto(userRepository.save(updatedUser))).thenReturn(userDto);

        UserDto result = userService.updateUser("uid", updateUserRequest);

        assertEquals(userDto, result);

        Mockito.verify(userRepository).findById("uid");
        Mockito.verify(userDtoConverter).convertToUserDto(userRepository.save(updatedUser));
    }

    @Test
    void testUpdateUser_whenGetUpdateUserRequestButInvalidId_itShouldThrowUserNotFoundException() {

        UpdateUserRequest updateUserRequest = generateUpdateUserRequest();

        Mockito.when(userRepository.findById("uid")).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userService.updateUser("uid",updateUserRequest));

        Mockito.verify(userRepository).findById("uid");
        Mockito.verifyNoInteractions(userDtoConverter);
    }

    @Test
    void testDeleteUser_whenGetValidId_itShouldReturnString() {

        User expectedUser = generateUser();
        UserDto expectedUserDto = generateUserDto();


        Mockito.when(userRepository.findById("id")).thenReturn(Optional.of(expectedUser));
        Mockito.when(userDtoConverter.convertToUserDto(expectedUser)).thenReturn(expectedUserDto);


        String result = userService.deleteUserById("id");

        assertEquals("user deleted successfully" + "id" , result);

        Mockito.verify(userRepository).findById("id");
        Mockito.verify(userDtoConverter).convertToUserDto(expectedUser);
    }

    @Test
    void testDeleteUser_whenGetInValidId_itShouldThrowUserNotFoundException() {

        Mockito.when(userRepository.findById("id")).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userService.getUserById("id"));

        Mockito.verify(userRepository).findById("id");
        Mockito.verifyNoInteractions(userDtoConverter);
    }

}
