package com.md.blog.dto.converter;

import com.md.blog.dto.CommentDto;
import com.md.blog.dto.PostDto;
import com.md.blog.dto.UserDto;
import com.md.blog.model.Comment;
import com.md.blog.model.Post;
import com.md.blog.model.User;
import org.springframework.stereotype.Component;

import com.md.blog.util.TimeUtil;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoConverter {

    public UserDto convertToUserDto(User from){
        return new UserDto(
                from.getUid(),
                from.getUsername(),
                from.getEmail(),
                from.getUpdatedDate(),
                getPostList(from.getPost()),
                getCommentList(from.getComment())
        );
    }
    public List<PostDto> getPostList(List<Post> posts){
        return posts.stream().map(
                p -> new PostDto(
                        p.getPid(),
                        p.getTitle(),
                        p.getBody(),
                        p.getPostTags(),
                        p.getCreationDate(),
                        p.getUpdatedDate()
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

}
