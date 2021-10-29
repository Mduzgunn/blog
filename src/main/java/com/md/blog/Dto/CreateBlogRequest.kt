package com.md.blog.Dto

import javax.validation.constraints.*

data class CreateBlogRequest(

        @field:NotBlank
        val id: String,
        @field:NotBlank(message = "isim boş bırakılamaz")
        val name: String,
        @field:NotNull
        @field:Max(value=100)
        @field:Min(value = 1)
        @field:Positive
        val age: Int,
        @field:Email
        val email: String


        )
