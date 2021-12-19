package com.github.forum.web

import com.github.forum.application.ForumReader
import com.github.forum.application.TopicDetails
import com.github.forum.domain.Topic
import com.github.forum.domain.User
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestConstructor.AutowireMode
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(ForumController::class)
@TestConstructor(autowireMode = AutowireMode.ALL)
internal class ForumControllerTests(val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var forumReader: ForumReader

    @Test
    fun `주제 목록 화면 접근시`() {
        val topics = arrayOf(
            TopicDetails.of(
                Topic.create("test", User.ANONYMOUS)
            ) {
                User.ANONYMOUS
            }
        )
        every { forumReader.loadTopics() } returns topics

        mockMvc.get("/forum/topics") {
            accept = MediaType.TEXT_HTML
        }.andExpect {
            status { isOk() }
            view { name("forum/topics") }
            model { attributeExists("topics") }
        }
    }
}
