package com.md.blog.service;

import com.md.blog.TestSupport;
import com.md.blog.dto.UserDto;
import com.md.blog.dto.converter.UserDtoConverter;
import com.md.blog.dto.requests.CreateUserRequest;
import com.md.blog.exception.NotFoundException;
import com.md.blog.model.User;
import com.md.blog.repository.UserRepository;
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


//    @Test
//    void testCreateMovie_whenPublisherIdNotExist_shouldThrowRuntimeException() {
//        User user = generateUser();
//        CreateUserRequest movieRequest = generateUserRequest();
//
//        Mockito.when(userService.getUserById("uid")).thenReturn(user);
//
//        assertThrows(RuntimeException.class,
//                () -> userService.createUser(movieRequest));
//
//        Mockito.verify(userRepository).getById("publisherId");
//
//    }

    /// test get all users

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

    ///get user by id test
    @Test
    void testGetUserById_whenCalledWithId_itShouldReturnUserDto() {
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
    void testGetUserById_whenIdNotExist_itShouldThrowNotFoundException() {

        Mockito.when(userRepository.findById("id")).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> userService.getUserById("id"));

        Mockito.verify(userRepository).findById("id");
        Mockito.verifyNoInteractions(userDtoConverter);
    }


    //test create user
//    @Test
//    void testCreateUser_whenCalledValidRequest_itShouldReturnUserDto() {
//        CreateUserRequest createUserRequest = generateCreateUserRequest();
//        User user = generateUser();
//        UserDto userDto = generateUserDto();
//
//        Mockito.when(userDtoConverter.convertToUserDto(user)).thenReturn(userDto);
//        Mockito.when(userRepository.save(user)).thenReturn(user);
//
//        UserDto result = userService.createUser(createUserRequest);
//
//        assertEquals(userDto, result);
//
//        Mockito.verify(userDtoConverter).convertToUserDto(user);
//        Mockito.verify(userRepository).save(user);
//    }

    // test delete user by id
    @Test
    void testDeleteUser_whenCalledValidId_itShouldReturnString() {

        User user = generateUser();
        UserDto userDto = generateUserDto();

        Mockito.when(userRepository.findById("id")).thenReturn(Optional.of(user));
        Mockito.when(userDtoConverter.convertToUserDto(user)).thenReturn(userDto);

        String result = userService.deleteUserById("id");

        assertEquals("user deleted successfully", result);

        Mockito.verify(userRepository).findById("id");
        Mockito.verify(userDtoConverter).convertToUserDto(user);
    }

    @Test
    void testDeleteUser_whenCalledInValidId_itShouldThrowNotFoundException() {

        Mockito.when(userRepository.findById("id")).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> userService.getUserById("id"));

        Mockito.verify(userRepository).findById("id");
        Mockito.verifyNoInteractions(userDtoConverter);
    }

}
