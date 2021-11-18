package com.md.blog.dto.requests

import com.md.blog.model.PostTags
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class UpdateUserRequest(

        @field:NotBlank
        val username: String,

        @field:Email
        val email: String

)
