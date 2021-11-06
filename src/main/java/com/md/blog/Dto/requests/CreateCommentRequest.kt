package com.md.blog.Dto.requests

import javax.validation.constraints.NotEmpty

data class CreateCommentRequest (
    //    * commments----
//    * cid,author,uid,pid,date_created
    @field:NotEmpty
    val uid: String,

    @field:NotEmpty
    val pid: String,

    @field:NotEmpty
    val comment: String,

)

