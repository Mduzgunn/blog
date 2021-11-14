package com.md.blog.dto.converter;

import com.md.blog.dto.CommentDto;
import com.md.blog.dto.UserDto;
import com.md.blog.model.Comment;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CommentDtoConverter {

    public CommentDto convertToCommentDto(Comment from){
        return new CommentDto(
                from.getCid(),
                from.getComment(),
                from.getCreationDate(),
                new UserDto(Objects.requireNonNull(from.getUser().getUid()),
                        from.getUser().getUsername(),
                        from.getUser().getEmail(),
                        Collections.emptyList(),
                        Collections.emptyList()
                        //from.getUser().getCreationDate()
                        )
        );
    }

    public List<CommentDto> convertToCommentDtoList(List<Comment> from) {
        return from
                .stream()
                .map(this::convertToCommentDto)
                .collect(Collectors.toList());
    }
}
    