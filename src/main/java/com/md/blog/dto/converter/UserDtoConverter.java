package com.md.blog.dto.converter;

import com.md.blog.dto.CommentDto;
import com.md.blog.dto.PostDto;
import com.md.blog.dto.UserDto;
import com.md.blog.model.Comment;
import com.md.blog.model.Post;
import com.md.blog.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserDtoConverter {

    public UserDto convertToUserDto(User from){
        return new UserDto(
                Objects.requireNonNull(from.getUid()),
                from.getUsername(),
                from.getEmail(),
                //from.getCreationDate(),
                getPostList(from.getPost()),
                getCommentList(from.getComment())
                //new ArrayList<>(getPostList(from.getPost())),
                //new ArrayList<>(getCommentList(from.getComment()))
        );
    }

    public List<PostDto> getPostList(List<Post> posts){
        return posts.stream().map(
                p -> new PostDto(
                        p.getPid(),
                        p.getTitle(),
                        p.getBody(),
                        p.getPostTags(),
                        p.getCreationDate()
                )
        ).collect(Collectors.toList());
    }

    public List<CommentDto> getCommentList(List<Comment> comments){
        return comments.stream().map(
                c -> new CommentDto(
                        c.getCid(),
                        c.getComment(),
                        c.getCreationDate()
                )
        ).collect(Collectors.toList());
    }

    public List<UserDto> convertToUserDtoList(List<User> from) {
        return from
                .stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
    }


//    public UserDto convert(User from) {
//        return new UserDto(
//                from.getId(),
//                from.getUsername(),
//                from.getEmail(),
//                from.getDisplayName(),
//                from.isActive(),
//                convertToPostDtoList(from.getPosts()),
//                getCommentList(from.getComments())
//        );
//    }
//
//    public List<UserDto> convertToUserDtoList(List<User> users) {
//        return users.stream().map(this::convert).collect(Collectors.toList());
//    }
}
