package com.md.blog.Model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.Entity
import javax.persistence.*

@Entity
data class User @JvmOverloads constructor(
        @Id
      //  @Column(name = "uid")
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val uid: String? = "",
        val username: String= "",
        val email: String="",

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        val post: Set<Post> = HashSet(),

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "user") //
        val comment: List<Comment>,


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (uid != other.uid) return false
        if (username != other.username) return false
        if (email != other.email) return false
        if (post != other.post) return false
        if (comment != other.comment) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uid?.hashCode() ?: 0
        result = 31 * result + username.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + post.hashCode()
        result = 31 * result + comment.hashCode()
        return result
    }
}
