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
import java.util.stream.Collectors;

@Component
public class PostDtoConverter {

    public PostDto convertToPostDto(Post from){
        return new PostDto(
                from.getPid(),
                from.getTitle(),
                from.getBody(),
                from.getPostTags(),
                from.getCreationDate(),
             new UserDto(from.getUser().getUid(),
                     from.getUser().getUsername(),
                     from.getUser().getEmail(),
                     from.getUser().getCreationDate()
                     ),
                new ArrayList<>(getCommentList(from.getComment()))

        );
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
