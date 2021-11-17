package com.md.blog.dto

import com.fasterxml.jackson.annotation.JsonInclude;
import com.md.blog.model.PostTags;
import java.time.LocalDateTime;

data class PostDto @JvmOverloads constructor(
        val pid: String?,
        val title: String,
        val body: String,
        val tags: PostTags,
        val creationDate: LocalDateTime = LocalDateTime.now(),
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val updatedDate: LocalDateTime?=null,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val author: UserDto?=null,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        val comments: List<CommentDto>?=ArrayList()


        )