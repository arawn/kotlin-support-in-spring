package com.github.forum.domain

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.data.relational.core.mapping.Column
import java.util.*


class Topic(
    @Id @Column("ID")
    private val _id: UUID,
    val title: String,
    val author: AggregateReference<User, Long>,
    val createdAt: Date,
    val updatedAt: Date): Persistable<UUID> {

    @Transient
    private var _isNew: Boolean= false

    override fun getId(): UUID {
        return _id;
    }

    override fun isNew(): Boolean {
        return _isNew
    }

    fun retitle(title: String): Topic {
        return Topic(_id, title, author, createdAt, Date())
    }

    fun writePost(text: String, author: User): Post {
        return Post(text, AggregateReference.to(author.requiredId()), AggregateReference.to(_id))
    }

    override fun toString() : String {
        return "Topic(id='$_id', title='$title', author=${author.id})"
    }

    companion object {

        fun create(title: String, author: User): Topic {
            val createdAt = Date()
            val topic = Topic(
                UUID.randomUUID(), title, AggregateReference.to(author.requiredId()), createdAt, createdAt
            )
            topic._isNew = true
            return topic
        }

    }

}
