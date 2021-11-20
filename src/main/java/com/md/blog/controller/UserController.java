package com.md.blog.controller;

import com.md.blog.dto.CommentDto;
import com.md.blog.dto.PostDto;
import com.md.blog.dto.UserDto;
import com.md.blog.dto.requests.CreateUserRequest;
import com.md.blog.dto.requests.UpdatePostRequest;
import com.md.blog.dto.requests.UpdateUserRequest;
import com.md.blog.model.Comment;
import com.md.blog.service.UserService;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtoList = userService.getAllUsers();
        userDtoList.forEach(userDto -> {
            Link userLink = linkTo(methodOn(UserController.class).getUserByID(userDto.getUid())).withSelfRel();
            userDto.add(userLink);
            getPostLinks(userDto.getPosts());

        });
        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserByID(@PathVariable String id) {
        UserDto userDto = userService.getUserById(id);
        getPostLinks(userDto.getPosts());
        getCommentLinks(userDto.getComments());
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userService.createUser(createUserRequest));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> updatePost(@PathVariable String id, @RequestBody UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.updateUser(id, updateUserRequest));
    }

    private void getPostLinks(List<PostDto> postDtoList){
        postDtoList.forEach(postDto -> {
            Link postLink = linkTo(methodOn(PostController.class)
                    .getPostByID(postDto.getPid())).withSelfRel();
            postDto.add(postLink);

        });
    }

    private void getCommentLinks(List<CommentDto> commentDtoList){
        commentDtoList.forEach(commentDto -> {
            Link commentLink = linkTo(methodOn(CommentController.class).getCommentByID(commentDto.getCid())).withSelfRel();
            commentDto.add(commentLink);
        });
    }



}
