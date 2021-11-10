package com.md.blog.dto

import java.time.LocalDateTime

data class UserDto @JvmOverloads constructor(
        //uid, username, email, date_created
        val uid: String,
        val username: String,
        val email: String,
        val creationDate: LocalDateTime,
        val posts : List<PostDto>? = ArrayList(),
        val comments: List<CommentDto>? = ArrayList()



//        val uid: String,
//        val username: String,
//        val email: String,
//        val creationDate: LocalDateTime,
//        val posts : List<UserPostDto>,
//        val comments: List<UserCommentDto>
)
