package com.md.blog.dto.requests

import com.md.blog.model.PostTags;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

data class UpdatePostRequest(
        @field:NotBlank
        val title: String,

         @field:NotBlank
         val body: String,

        @field:Enumerated(EnumType.STRING)
        val postTags: PostTags = PostTags.CODE

)
