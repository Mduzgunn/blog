package com.md.blog.model

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Comment @JvmOverloads constructor(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val cid: String? = "",
        val comment: String,
        val creationDate: LocalDateTime,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", referencedColumnName = "uid")
        val user: User,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "post_id", referencedColumnName = "pid")
        val post: Post,

        ) {
    constructor(comment:String, user:User, post:Post): this (
            "",
            comment=comment,
            creationDate=LocalDateTime.now(),
            user = user,
            post = post
            )
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Comment

        if (cid != other.cid) return false
        if (comment != other.comment) return false
        if (user != other.user) return false
        if (post != other.post) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cid?.hashCode() ?: 0
        result = 31 * result + comment.hashCode()
        result = 31 * result + user.hashCode()
        result = 31 * result + post.hashCode()
        return result
    }


}
