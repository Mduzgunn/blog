package com.md.blog.dto.requests

import javax.validation.constraints.NotEmpty

data class CreateCommentRequest (
    //    * commments----

    @field:NotEmpty
    val uid: String,

    @field:NotEmpty
    val pid: String,

    @field:NotEmpty
    val comment: String,

)

