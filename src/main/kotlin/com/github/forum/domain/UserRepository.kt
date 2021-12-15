package com.github.forum.domain

import org.springframework.transaction.annotation.Transactional

@Transactional
interface UserRepository {

    fun save(entity: User): User
}
