package com.github.forum.application

import com.github.forum.domain.PostQueryRepository
import com.github.forum.domain.Topic
import com.github.forum.domain.TopicQueryRepository
import com.github.forum.domain.User
import com.github.forum.domain.UserQueryRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.util.UUID

@Service
@Transactional(readOnly = true)
class ForumQueryService(
    val executor: ThreadPoolTaskExecutor,
    val userQueryRepository: UserQueryRepository,
    val topicQueryRepository: TopicQueryRepository,
    val postQueryRepository: PostQueryRepository
) : ForumNewsPublisher, ForumReader {

    override fun subscribe(): News {
        val sample = TopicDetails.of(
            Topic.create("sample", User.ANONYMOUS),
            { User.ANONYMOUS }
        )
        return News(
            arrayOf(
                NewsItem.NewTopic(sample)
            )
        )
    }

    override suspend fun subscribe(period: Duration): News {
        return coroutineScope {
            delay(period.toMillis())
            subscribe()
        }
    }

    //
    // Mono.fromSupplier {
    //     subscribe()
    // }.delaySubscription(
    // period
    // ).publishOn(
    // Schedulers.fromExecutor(executor)
    // )

    override fun loadTopics(): Array<TopicDetails> {
        return topicQueryRepository.findAll().map {
            TopicDetails.of(it, { ref ->
                userQueryRepository.findById(ref.id!!)
            })
        }.toTypedArray()
    }

    override fun loadPosts(topicId: UUID): Posts {
        val topic = topicQueryRepository.findById(topicId)
        val posts = postQueryRepository.findByTopic(topic)
        return Posts(
            TopicDetails.of(
                topic,
                { ref ->
                    userQueryRepository.findById(ref.id!!)
                }
            ),
            posts
        )
    }
}
