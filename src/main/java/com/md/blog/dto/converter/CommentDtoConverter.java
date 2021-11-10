package com.md.blog.dto.converter;

import com.md.blog.dto.CommentDto;
import com.md.blog.dto.UserDto;
import com.md.blog.model.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentDtoConverter {


    public CommentDto convertToCommentDto(Comment from){
        return new CommentDto(
                from.getCid(),
                from.getComment(),
                from.getCreationDate(),
                new UserDto(from.getUser().getUid(),
                        from.getUser().getUsername(),
                        from.getUser().getEmail(),
                        from.getUser().getCreationDate())
        );
    }

}
    