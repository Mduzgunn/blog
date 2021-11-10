package com.md.blog.dto

import com.md.blog.model.PostTags
import java.time.LocalDateTime

data class PostDto @JvmOverloads constructor(
        //pid, title, body,author, date_created,tags
        val pid: String,
        val title: String,
        val body: String,
        val creationDate: LocalDateTime,
        val tags: PostTags,
        val author: UserDto? = null,
        val commentList: List<CommentDto>? = ArrayList()
        // val comment: CommentDto? = null

        //set testlerde sıralama yüzünden sorun olabilir





//        //pid, title, body,author, date_created,tags
//        val pid: String,
//        val title: String,
//        val body: String,
//        val author:PostUserDto,
//        val creationDate: LocalDateTime,
//        val tags: PostTags,
//        //set testlerde sıralama yüzünden sorun olabilir
//        val comments: List<PostCommentDto>
        )
