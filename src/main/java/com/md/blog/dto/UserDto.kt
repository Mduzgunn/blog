package com.md.blog.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

data class UserDto @JvmOverloads constructor(
        val uid: String?,
        val username: String,
        val email: String,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val updatedDate: LocalDateTime?=null,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        val posts : List<PostDto>?=ArrayList(),
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        val comments: List<CommentDto>?=ArrayList()

)
