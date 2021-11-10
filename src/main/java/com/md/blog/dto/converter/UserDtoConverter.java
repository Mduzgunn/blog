package com.md.blog.dto.converter;

import com.md.blog.dto.CommentDto;
import com.md.blog.dto.PostDto;
import com.md.blog.dto.UserDto;
import com.md.blog.model.Comment;
import com.md.blog.model.Post;
import com.md.blog.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoConverter {

    public UserDto convertToUserDto(User from){
        return new UserDto(
                from.getUid(),
                from.getUsername(),
                from.getEmail(),
                from.getCreationDate(),
                getPostList(from.getPost()).stream().collect(Collectors.toList()),
                getCommentList(from.getComment()).stream().collect(Collectors.toList())
        );
    }

    public List<PostDto> getPostList(List<Post> posts){
        return posts.stream().map(
                p -> new PostDto(
                        p.getPid(),
                        p.getTitle(),
                        p.getBody(),
                        p.getCreationDate(),
                        p.getPostTags()
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
}
