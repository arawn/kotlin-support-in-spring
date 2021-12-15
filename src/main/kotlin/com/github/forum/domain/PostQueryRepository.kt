package com.github.forum.domain

import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface PostQueryRepository {

    fun findByTopic(topic: Topic): Array<Post>
}
