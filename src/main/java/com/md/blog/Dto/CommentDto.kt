package com.md.blog.Dto


data class CommentDto(
        val cid: String,
        val comment: String,

        val uid: String,
        val author: String,
        val pid: String

)
