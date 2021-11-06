package com.md.blog.Dto.requests

import javax.validation.constraints.*

data class CreatePostRequest(

        //TODO

        /*

        *
        * posts------
        * pid, title, body, user_id, author, date_created
        *

        * */

        @field:NotBlank
        val pid: String,

        @field:NotBlank(message = "başlık boş bırakılamaz")
        val title: String,

        @field:NotBlank(message = "içerik boş bırakılamaz")
        val body: String,

        @field:NotBlank(message = "yazar boş bırakılamaz")
        val author: String,


//        @field:NotBlank
//        val id: String,
//        @field:NotBlank(message = "isim boş bırakılamaz")
//        val name: String,
//        @field:NotNull
//        @field:Max(value=100)
//        @field:Min(value = 1)
//        @field:Positive
//        val age: Int,
//        @field:Email
//        val email: String


        )
