package com.md.blog.model

import org.hibernate.Hibernate
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "blog_user")
data class User @JvmOverloads constructor(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val uid: String? = "",
        val username: String,
        val email: String,

        val creationDate: LocalDateTime = LocalDateTime.now(),

        val updatedDate: LocalDateTime = LocalDateTime.now(),

        @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = [CascadeType.ALL])
        val post: List<Post>?=ArrayList(),

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "user",cascade = [CascadeType.ALL]) //
        val comment: List<Comment>?=ArrayList(),


        )
    {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
            other as User

            return uid != null && uid == other.uid
        }

        override fun hashCode(): Int = javaClass.hashCode()
}

