package com.md.blog.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

data class UserDto @JvmOverloads constructor(
        //uid, username, email, date_created
        val uid: String?,
        val username: String,
        val email: String,
        val creationDate: LocalDateTime,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        val posts : List<PostDto>? = ArrayList(),
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        val comments: List<CommentDto>? = ArrayList()



//        val uid: String,
//        val username: String,
//        val email: String,
//        val creationDate: LocalDateTime,
//        val posts : List<UserPostDto>,
//        val comments: List<UserCommentDto>
)
