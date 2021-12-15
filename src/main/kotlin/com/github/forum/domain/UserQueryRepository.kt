package com.github.forum.domain

import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface UserQueryRepository {

    fun findById(id: Long): User
}
