package com.github.forum.domain

import org.springframework.transaction.annotation.Transactional

@Transactional
interface PostRepository {

    fun save(entity: Post): Post
}
