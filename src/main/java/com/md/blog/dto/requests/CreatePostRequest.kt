package com.md.blog.dto.requests

import com.md.blog.model.PostTags
import javax.validation.constraints.*

data class CreatePostRequest(


        @field:NotBlank(message = "başlık boş bırakılamaz")
        val title: String,

        @field:NotBlank(message = "içerik boş bırakılamaz")
        val body: String,

        @field:NotBlank(message = "lütfen bir tag seçin")
        val tags: PostTags,

        @field:NotBlank(message = "yazar boş bırakılamaz")
        val author: String?
)
