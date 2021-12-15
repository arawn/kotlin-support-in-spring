package com.github.forum.application.support

import com.github.forum.ForumApplication
import com.github.forum.domain.PostRepository
import com.github.forum.domain.Topic
import com.github.forum.domain.TopicRepository
import com.github.forum.domain.User
import com.github.forum.domain.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Profile(ForumApplication.PROFILE_LOCAL)
@Component
class LocalForumDataInitializer(
    val userRepository: UserRepository,
    val topicRepository: TopicRepository,
    val postRepository: PostRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostConstruct
    fun registration() {
        val max = userRepository.save(User.create("max"))
        val root = userRepository.save(User.create("root"))
        val arawn = userRepository.save(User.create("arawn"))

        val di = topicRepository.save(Topic.create("Dependency injection", max))
        postRepository.save(di.writePost("first post", root))
        postRepository.save(di.writePost("second post", arawn))
        postRepository.save(di.writePost("third post", root))

        val cfd = topicRepository.save(Topic.create("Classes final by default", arawn))
        postRepository.save(cfd.writePost("first post", max))
        postRepository.save(cfd.writePost("second post", root))

        log.info("Registered basic data ({}, {})", di, cfd)
    }
}
