package com.md.blog.dto.requests

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateUserRequest(

        @field:NotBlank
        val username: String,

        @field:NotBlank
        @field:Email
        val email: String,


)