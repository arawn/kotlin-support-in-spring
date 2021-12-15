package com.github.forum.domain

import org.springframework.transaction.annotation.Transactional

@Transactional
interface TopicRepository {

    fun save(entity: Topic): Topic
}
