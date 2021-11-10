package com.md.blog.dto

import java.time.LocalDateTime


data class CommentDto @JvmOverloads constructor(
        //cid,comment,author,date_created
        val cid: String,
        val comment: String,
        val creationDate: LocalDateTime,
        val author: UserDto? = null

//        val postList: List<CommentPostDto>? = ArrayList()
        )
