package com.github.forum.web

import com.github.forum.application.ForumNewsPublisher
import com.github.forum.application.News
import com.github.forum.application.NewsItem
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.Duration

@RestController
@RequestMapping("/notification")
class NotificationController(val forumNewsPublisher: ForumNewsPublisher) {

    @GetMapping("/subscribe/news")
    fun subscribeNews(): Mono<NewsDto> {
        return forumNewsPublisher.subscribeAsync(
            Duration.ofSeconds(5)
        ).flatMap({ news ->
            Mono.just(
                NewsDto.of(news)
            )
        })
    }
}

data class NewsItemDto(val text: String, val author: Any?)
data class NewsDto(val items: Array<NewsItemDto>) {

    companion object {

        fun of(news: News) = NewsDto(
            news.items.map({ item -> mapItem(item) }).toTypedArray()
        )

        private fun mapItem(item: NewsItem<*>): NewsItemDto {
            if (item is NewsItem.NewTopic) {
                return NewsItemDto(item.content.title, item.content.author.username)
            } else if (item is NewsItem.NewPost) {
                return NewsItemDto(item.content.text, item.content.author)
            } else {
                throw IllegalArgumentException("This item cannot be converted")
            }
        }
    }
}
