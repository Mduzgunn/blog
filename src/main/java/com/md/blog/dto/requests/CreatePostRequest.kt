package com.md.blog.dto.requests

import com.md.blog.model.PostTags
import com.md.blog.model.User
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.*

data class CreatePostRequest(


        @field:NotBlank(message = "başlık boş bırakılamaz")
        val title: String,

        @field:NotBlank(message = "içerik boş bırakılamaz")
        val body: String,

        @field:Enumerated(EnumType.STRING)
        val tags: PostTags,

        @field:NotNull(message = "yazar boş bırakılamaz")
        val uid: String
)
