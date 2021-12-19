package com.github.forum.web

import com.github.forum.application.ForumNewsPublisher
import com.github.forum.application.News
import com.github.forum.application.NewsItem
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
@RequestMapping("/notification")
class NotificationController(val forumNewsPublisher: ForumNewsPublisher) {

    @GetMapping("/subscribe/news")
    suspend fun subscribeNews(): NewsDto {
        return NewsDto.of(
            forumNewsPublisher.subscribe(Duration.ofSeconds(5))
        )
    }
}

data class NewsItemDto(val text: String, val author: Any?)
data class NewsDto(val items: Array<NewsItemDto>) {

    companion object {

        fun of(news: News) = NewsDto(
            news.items.map({ item -> mapItem(item) }).toTypedArray()
        )

        private fun mapItem(item: NewsItem<*>) = when (item) {
            is NewsItem.NewTopic -> NewsItemDto(item.content.title, item.content.author.username)
            is NewsItem.NewPost -> NewsItemDto(item.content.text, item.content.author)
        }
    }
}
