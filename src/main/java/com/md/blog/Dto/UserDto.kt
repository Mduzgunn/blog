package com.md.blog.Dto

import javax.validation.constraints.Email

data class UserDto(
        val uid:String,
        val username:String,
        val email: String
)
