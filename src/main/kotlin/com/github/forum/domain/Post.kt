package com.github.forum.domain

import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.core.mapping.AggregateReference
import java.util.Date
import java.util.UUID

class Post(
    @Id
    val id: Long? = null,
    val text: String,
    val author: AggregateReference<User, Long>,
    val topic: AggregateReference<Topic, UUID>,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) {

    // for spring-data-jdbc, parameter must be of nullable-types
    private fun withId(id: Long?): Post {
        return Post(id, text, author, topic, createdAt, updatedAt)
    }
}
