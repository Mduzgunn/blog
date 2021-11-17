package com.md.blog.model

import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;

@Entity
data class Post @JvmOverloads constructor(

        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val pid: String? = "",

        val title: String,
        val body: String,
        val postTags: PostTags,

        val creationDate: LocalDateTime= LocalDateTime.now(),

        val updatedDate: LocalDateTime = LocalDateTime.now(),

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "uid")
        val user: User,

        @OneToMany(mappedBy = "post", fetch = FetchType.LAZY,cascade = [CascadeType.ALL])
        val comment: List<Comment>,


        ) {

    constructor(title: String, body: String, postTags: PostTags,user: User) : this(
            "",
            title = title,
            body = body,
            postTags = postTags,
            creationDate = LocalDateTime.now(),
            updatedDate = LocalDateTime.now(),
            user = user,
            comment= Collections.emptyList()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (pid != other.pid) return false
        if (title != other.title) return false
        if (body != other.body) return false
        if (postTags != other.postTags) return false
        if (creationDate != other.creationDate) return false
        if (updatedDate != other.updatedDate) return false
        if (user != other.user) return false
        if (comment != other.comment) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pid?.hashCode() ?: 0
        result = 31 * result + title.hashCode()
        result = 31 * result + body.hashCode()
        result = 31 * result + postTags.hashCode()
        result = 31 * result + creationDate.hashCode()
        result = 31 * result + updatedDate.hashCode()
        result = 31 * result + user.hashCode()
        result = 31 * result + comment.hashCode()
        return result
    }
}

enum class PostTags {
    CODE, HISTORY, CUSTOM
}