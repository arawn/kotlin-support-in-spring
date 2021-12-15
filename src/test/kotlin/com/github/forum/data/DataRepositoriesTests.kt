package com.github.forum.data

import com.github.forum.domain.Topic
import com.github.forum.domain.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace

@DataJdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
internal class DataRepositoriesTests {

    @Autowired
    lateinit var userRepository: SpringDataUserRepository

    @Autowired
    lateinit var topicRepository: SpringDataTopicRepository

    @Autowired
    lateinit var topicQueryRepository: SpringDataTopicQueryRepository

    @Autowired
    lateinit var postRepository: SpringDataPostRepository

    @Autowired
    lateinit var postQueryRepository: SpringJdbcPostQueryRepository

    @Test
    fun `주제 작성 후 조회하기`() {
        val user = userRepository.save(User.create("arawn"))

        val created = topicRepository.save(Topic.create("Testing", user))
        Assertions.assertNotNull(created.id)
        Assertions.assertTrue(created.isNew)

        val loaded = topicQueryRepository.findById(created.id)
        Assertions.assertFalse(loaded.isNew)
        Assertions.assertEquals(created.id, loaded.id)
        Assertions.assertEquals(created.title, loaded.title)
    }

    @Test
    fun `주제별 글 쓴 후 조회하기`() {
        val user = userRepository.save(User.create("arawn"))

        val springTopic = topicRepository.save(Topic.create("spring topic", user))
        val post = postRepository.save(springTopic.writePost("post in spring", user))
        Assertions.assertNotNull(post.id)
        Assertions.assertEquals(1, postRepository.findAll().count())

        val kotlinTopic = topicRepository.save(Topic.create("kotlin topic", user))
        val firstPost = postRepository.save(kotlinTopic.writePost("first post in kotlin", user))
        val secondPost = postRepository.save(kotlinTopic.writePost("second post in kotlin", user))
        Assertions.assertEquals(2, postQueryRepository.findByTopic(kotlinTopic).count())
    }
}
