package com.md.blog.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.md.blog.model.PostTags
import com.md.blog.model.User
import java.time.LocalDateTime

data class PostDto @JvmOverloads constructor(
        //pid, title, body,author, date_created,tags
        val pid: String?,
        val title: String,
        val body: String,
        val tags: PostTags,
        val creationDate: LocalDateTime,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        val author: UserDto?=null,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        val comments: List<CommentDto>?=ArrayList(),


        )