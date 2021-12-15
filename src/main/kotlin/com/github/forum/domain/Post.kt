package com.github.forum.domain

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.jdbc.core.mapping.AggregateReference
import java.util.Date
import java.util.UUID

class Post @PersistenceConstructor constructor(
    @Id
    val id: Long?,
    val text: String,
    val author: AggregateReference<User, Long>,
    val topic: AggregateReference<Topic, UUID>,
    val createdAt: Date,
    val updatedAt: Date
) {
    constructor(text: String, author: AggregateReference<User, Long>, topic: AggregateReference<Topic, UUID>, createdAt: Date) : this(null, text, author, topic, createdAt, createdAt)
    constructor(text: String, author: AggregateReference<User, Long>, topic: AggregateReference<Topic, UUID>) : this(text, author, topic, Date())

    // for spring-data-jdbc, parameter must be of nullable-types
    private fun withId(id: Long?): Post {
        return Post(id, text, author, topic, createdAt, updatedAt)
    }
}
