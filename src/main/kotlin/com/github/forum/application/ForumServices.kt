package com.github.forum.application

import com.github.forum.domain.Post
import com.github.forum.domain.Topic
import com.github.forum.domain.User
import org.springframework.data.jdbc.core.mapping.AggregateReference
import java.time.Duration
import java.util.Date
import java.util.UUID

interface ForumNewsPublisher {

    fun subscribe(): News

    suspend fun subscribe(period: Duration): News
}

sealed class NewsItem<out C> {
    val type: String
        get() = javaClass.simpleName
    abstract val content: C

    data class NewTopic(override val content: TopicDetails) : NewsItem<TopicDetails>()
    data class NewPost(override val content: Post) : NewsItem<Post>()
}
data class News(val items: Array<NewsItem<*>>)

interface ForumReader {

    fun loadTopics(): Array<TopicDetails>

    fun loadPosts(topicId: UUID): Posts
}

class TopicDetails private constructor(
    val id: UUID,
    val title: String,
    val author: User,
    val createdAt: Date,
    val updatedAt: Date
) {

    companion object {

        fun of(topic: Topic, authorMapper: (ref: AggregateReference<User, Long>) -> User): TopicDetails {
            return TopicDetails(
                id = topic.id,
                title = topic.title,
                author = authorMapper(topic.author),
                createdAt = topic.createdAt,
                updatedAt = topic.updatedAt
            )
        }
    }
}

fun interface AuthorMapper {
    fun map(ref: AggregateReference<User, Long>): User
}

data class Posts(val topic: TopicDetails, val content: Array<Post>)
