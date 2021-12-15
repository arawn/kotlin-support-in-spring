package com.github.forum.web

import com.github.forum.application.AuthorMapper
import com.github.forum.application.ForumReader
import com.github.forum.application.TopicDetails
import com.github.forum.domain.Topic
import com.github.forum.domain.User
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@WebMvcTest(ForumController::class)
internal class ForumControllerTests(@Autowired val mockMvc: MockMvc) {

    @MockBean
    private lateinit var forumReader: ForumReader

    @Test
    fun `주제 목록 화면 접근시`() {
        val topics = arrayOf(
            TopicDetails.of(
                Topic.create("test", User.ANONYMOUS),
                object : AuthorMapper {
                    override fun map(ref: AggregateReference<User, Long>): User {
                        return User.ANONYMOUS
                    }
                }
            )
        )
        given(forumReader.loadTopics()).willReturn(topics)

        mockMvc.perform(
            get("/forum/topics").accept(MediaType.TEXT_HTML)
        ).andExpect(
            status().isOk
        ).andExpect(
            view().name("forum/topics")
        ).andExpect(
            model().attributeExists("topics")
        )
    }
}
