package com.md.blog.dto.requests

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class CreateUserRequest(
        @field:NotEmpty()
        val username: String,
        @field:Email
        val email: String,


)