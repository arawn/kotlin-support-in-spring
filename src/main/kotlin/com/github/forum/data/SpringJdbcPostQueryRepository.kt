package com.github.forum.data

import com.github.forum.domain.Post
import com.github.forum.domain.PostQueryRepository
import com.github.forum.domain.Topic
import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import java.util.UUID

class SpringJdbcPostQueryRepository(val namedParameterJdbcOperations: NamedParameterJdbcOperations) : PostQueryRepository {

    val jdbcOperations: JdbcOperations
        get() = namedParameterJdbcOperations.jdbcOperations

    @Suppress("DEPRECATION")
    override fun findByTopic(topic: Topic): Array<Post> {
        return jdbcOperations.query(SQL_findByTopic, arrayOf(topic.id), {
            rs, rowNum ->
            Post(
                rs.getLong("ID"),
                rs.getString("TEXT"),
                AggregateReference.to(rs.getLong("AUTHOR")),
                AggregateReference.to(UUID.fromString(rs.getString("TOPIC"))),
                rs.getDate("CREATED_AT"),
                rs.getDate("UPDATED_AT")
            )
        }).toTypedArray()
    }

    companion object {
        const val SQL_findByTopic =
            """
            SELECT ID
                 , TEXT
                 , AUTHOR
                 , TOPIC
                 , CREATED_AT
                 , UPDATED_AT
              FROM POST
             WHERE TOPIC = ?
            """
    }
}
