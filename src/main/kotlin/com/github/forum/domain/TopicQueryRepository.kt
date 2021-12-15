package com.github.forum.domain

import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional(readOnly = true)
interface TopicQueryRepository {

    fun findById(id: UUID): Topic

    fun findAll(): Array<Topic>
}
