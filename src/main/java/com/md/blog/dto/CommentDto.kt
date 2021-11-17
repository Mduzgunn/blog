package com.md.blog.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime


data class CommentDto @JvmOverloads constructor(
        val cid: String?,
        val comment: String,
        val creationDate: LocalDateTime,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        val author: UserDto?=null
        )

