package com.github.forum.web

import com.github.forum.application.ForumReader
import com.github.forum.application.TopicDetails
import com.github.forum.domain.Post
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.Date
import java.util.UUID

@Controller
@RequestMapping("/forum")
class ForumController(val forumReader: ForumReader) {

    @GetMapping("/topics")
    fun topics(model: Model) {
        model.addAttribute("topics", TopicDto.from(forumReader.loadTopics()))
    }

    @GetMapping("/{topicId}/posts")
    fun postsWithTopic(@PathVariable topicId: UUID, model: Model) {
        val posts = forumReader.loadPosts(topicId)

        model.addAttribute("topic", TopicDto.from(posts.topic))
        model.addAttribute("posts", PostDto.from(posts.content.map { it!! }.toTypedArray()))
    }
}

data class TopicDto(
    val id: UUID,
    val title: String,
    val author: String,
    val createdAt: Date,
    val updatedAt: Date
) {

    companion object {

        fun from(topic: TopicDetails): TopicDto {
            return TopicDto(
                topic.id,
                topic.title,
                topic.author.username,
                topic.createdAt,
                topic.updatedAt
            )
        }

        fun from(topics: Array<TopicDetails>): Array<TopicDto> {
            return topics.map(this::from).toTypedArray()
        }
    }
}
data class PostDto(
    val id: Long,
    val text: String,
    val author: Long?,
    val createdAt: Date,
    val updatedAt: Date
) {

    companion object {

        fun from(posts: Array<Post?>): Array<PostDto> {
            return posts.map({ post ->
                if (post == null) {
                    throw NullPointerException("Post object is null")
                }
                if (post.id == null) {
                    throw NullPointerException("Id field is null in post object")
                }
                PostDto(
                    post.id,
                    post.text,
                    post.author.id,
                    post.createdAt,
                    post.updatedAt
                )
            }).toTypedArray()
        }
    }
}
