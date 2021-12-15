package com.github.forum.domain

import org.springframework.data.annotation.Id
import java.util.Date

class User(
    @Id
    val id: Long?,
    val username: String,
    val createdAt: Date,
    val updatedAt: Date
) {

    fun requiredId() = id ?: throw IllegalStateException("사용자가 준비되지 않았어요")

    // for spring-data-jdbc, parameter must be of nullable-types
    private fun withId(id: Long?): User {
        return User(id, username, createdAt, updatedAt)
    }

    companion object {

        val ANONYMOUS = create("anonymous").withId(Long.MIN_VALUE)

        fun create(username: String): User {
            val createdAt = Date()
            return User(null, username, createdAt, createdAt)
        }
    }
}
